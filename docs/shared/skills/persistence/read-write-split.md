# Skill: Read/Write Persistence Split (CQRS)

## Category: persistence
## Provides:
- Read Write Split
## Conflicts With:
- None
## Depends On:
- @shared/skills/patterns/cqrs-and-events

This skill defines the law for separating Write Models (Aggregates) and Read Models (Projections) in a CQRS architecture.

## Principles
1.  **Logical Separation**: Write Models and Read Models are separate conceptual layers.
2.  **Physical Options**:
    - **Same Database, Separate Schemas**: Read models live in a `projections` schema within the same PostgreSQL instance as aggregates. This is the recommended starting point for most SaaS.
    - **Read Replica**: Read models are served from PostgreSQL read replicas fed by streaming replication. Suitable for read-heavy workloads.
    - **Dedicated Read Store**: Read models are materialized in a separate store (e.g., Elasticsearch, ClickHouse, or a separate PostgreSQL instance) via CDC or event consumption.
3.  **No Inline Updates**: Command Handlers MUST NOT write to Read Models. Read Models are updated exclusively by:
    - Event subscribers reacting to domain events.
    - CDC pipelines (e.g., Debezium) streaming changes from the write model.
    - Database triggers or materialized view refreshes (infrastructure concern).

## Synchronization Mechanics
1.  **Event-Driven Projections (Primary)**: Read Models are updated by event subscribers within the same module. The subscriber consumes a domain event and writes to the projection table.
2.  **CDC Projections (Advanced)**: For cross-module or analytical projections, use Change Data Capture (Debezium) to stream aggregate changes to a dedicated read store.
3.  **Consistency**: Read Models are eventually consistent. The typical latency between write and read model update is milliseconds.

## Technical Requirements
- **PostgreSQL**: Single primary instance with optional read replicas.
- **Projections Schema**: Read model tables SHOULD reside in a dedicated schema (e.g., `projections`) within the primary database for operational simplicity.
- **Connection Pools**: If using read replicas, the Query Layer MUST use the replica connection pool.
