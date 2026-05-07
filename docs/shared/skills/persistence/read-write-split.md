# Skill: Read/Write Persistence Split (Strict CQRS)

This skill defines the law for separating the physical storage of Write Models (Aggregates) and Read Models (Projections).

## Principles
1.  **Physical Separation**: The system MUST connect to two distinct database instances: `WRITE_DB` and `READ_DB`.
2.  **Connection Segregation**:
    - **Command Layer**: MUST only use the `WRITE_DB` connection for state changes.
    - **Query Layer**: MUST only use a "Read-Only" user/connection on the `READ_DB`.
    - **Subscriber Layer**: MUST use a "Read-Write" connection on the `READ_DB` to update projections.
3.  **Schema Segregation**:
    - The `WRITE_DB` schema contains the Normalized Relational Model (Aggregates).
    - The `READ_DB` schema contains the Denormalized View Model (Projections).

## Synchronization Mechanics
1.  **Application-Level Sync**: Projections are updated by Domain Events emitted from the Command Layer.
2.  **Infrastructure-Level Sync (CDC)**: Changes to the `WRITE_DB` are captured via the WAL (Write-Ahead Log) and propagated to the `READ_DB`. 
3.  **Local Implementation**: In development, we use **Event-Driven Projections** where the subscriber writes across the database boundary into the `READ_DB`.

## Technical Requirements
- **Postgres Instances**: Two separate containers (`db-write` and `db-read`).
- **Connection Pools**: The application MUST maintain two distinct connection pools.
