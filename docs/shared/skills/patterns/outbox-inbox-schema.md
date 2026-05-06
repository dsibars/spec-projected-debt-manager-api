# Skill: Pattern - Outbox & Inbox Schema

This skill defines the standard SQL schema for implementing the Outbox and Inbox patterns consistently across all modules.

## 1. Outbox Table Schema
Each module requiring the Outbox pattern must include this table in its schema.

```sql
CREATE TABLE outbox_events (
    id UUID PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    payload JSONB NOT NULL,
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE, -- NULL if not yet sent to broker
    retry_count INTEGER DEFAULT 0
);

CREATE INDEX idx_outbox_unprocessed ON outbox_events (occurred_at) WHERE processed_at IS NULL;
```

## 2. Inbox Table Schema (Processed Events)
Each module requiring the Inbox pattern must include this table to track processed event IDs.

```sql
CREATE TABLE processed_events (
    event_id UUID PRIMARY KEY,
    handler_name VARCHAR(255) NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
```

## Implementation Rules
- **Atomic Persistence**: Saving a domain entity and inserting into `outbox_events` MUST happen within the same database transaction.
- **Idempotency Check**: Before processing an event, the Handler MUST check if the `event_id` already exists in `processed_events`. The insertion into `processed_events` and the Read Model update MUST happen within the same transaction.
