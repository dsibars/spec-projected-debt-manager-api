# Skill: Pattern - Outbox & Inbox Schema

## Category: patterns
## Provides:
- Outbox Inbox Schema
## Conflicts With:
- clean-code
- cqrs-and-events
- data-replication
- module-boundaries
- resilience
- simplified-ddd
- tenant-load-balancing
## Depends On:
- None explicitly declared


This skill defines the standard SQL schema for implementing the Outbox and Inbox patterns consistently across all modules.

## 1. Outbox Table Schema
- **Location**: MUST reside in the `WRITE_DB` of the module emitting events.
- **Goal**: Ensure atomic persistence with the Domain Aggregate change.

```sql
CREATE TABLE outbox_events (
    id UUID PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    payload JSONB NOT NULL,
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE, -- NULL if not yet sent to broker
    retry_count INTEGER DEFAULT 0
);
```

## 2. Inbox Table Schema (Processed Events)
- **Location**: MUST reside in the same database as the target of the update.
    - If updating a Projection $\rightarrow$ `READ_DB`.
    - If updating an Aggregate $\rightarrow$ `WRITE_DB`.
- **Goal**: Ensure idempotency by tracking processed `event_id` in the same transaction as the state change.

```sql
CREATE TABLE processed_events (
    event_id UUID PRIMARY KEY,
    handler_name VARCHAR(255) NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
```

## Implementation Rules
- **Atomic Persistence**: Saving a domain entity and inserting into `outbox_events` MUST happen within the same database transaction.
- **Idempotency Check**: The insertion into `processed_events` and the model update MUST happen within the same transaction.