# Skill: Read/Write Persistence Split (Strict CQRS)

## Category: persistence
## Provides:
- Read Write Split
## Conflicts With:
- migrations
- postgresql
- repository-pattern
- seeding
## Depends On:
- None explicitly declared


This skill defines the law for separating the physical storage of Write Models (Aggregates) and Read Models (Projections).

## Principles
1.  **Physical Separation**: The system MUST connect to two distinct database instances: `WRITE_DB` and `READ_DB`.
2.  **Connection Segregation**:
    - **Command Layer**: MUST use the `WRITE_DB` connection for aggregate persistence.
    - **Query Layer**: MUST only use the `READ_DB`.
    - **Dual-Write (Inline)**: If a projection is marked for inline updates, the Command Layer is granted permission to use a `READ_DB` connection within its execution context.
3.  **Schema Segregation**:
    - The `WRITE_DB` schema contains the Normalized Relational Model (Aggregates).
    - The `READ_DB` schema contains the Denormalized View Model (Projections).

## Synchronization Mechanics
1.  **Application-Level Sync (Event-Driven)**: Cross-module projections are updated by Domain Events via subscribers writing to the `READ_DB`.
2.  **Inline Sync (Atomic)**: High-priority projections are updated by the Command Layer directly to the `READ_DB` to ensure read-your-own-write consistency.

## Technical Requirements
- **Postgres Instances**: Two separate containers (`db-write` and `db-read`).
- **Connection Pools**: The application MUST maintain distinct connection pools for `WRITE_DB` and `READ_DB`.