# Skill: Persistence - Repository & DataSource Strategy

## Category: persistence
## Provides:
- Repository Pattern
## Conflicts With:
- None
## Depends On:
- @shared/skills/persistence/postgresql

This skill defines how the Builder should generate the data access layer for a multi-tenant SaaS architecture.

## 1. DataSource Strategy
The application MUST maintain distinct connection pools:
- **`PrimaryDataSource`**: Points to the primary PostgreSQL instance. Used for all write operations on Aggregates and for projections co-located in the same database.
- **`ReplicaDataSource`** (Optional): Points to PostgreSQL read replicas. Used for Query operations on read-heavy projections.

## 2. Structural Patterns

### Command Side (Write)
- **Pattern**: **Aggregate Repository**.
- **Scope**: One repository per Aggregate Root defined in `models/`.
- **Injection**: MUST be injected with the `PrimaryDataSource`.
- **Naming**: `[EntityName]Repository` (e.g., `DebtRepository`, `PersonRepository`).

### Query Side (Read)
- **Pattern**: **Data Access Object (DAO) / Reader**.
- **Scope**: One reader per Projection or Read Model defined in `projections/`.
- **Injection**: Injected with `PrimaryDataSource` or `ReplicaDataSource`.
- **Naming**: `[ProjectionName]Reader` (e.g., `DebtSummaryReader`, `PersonReadModelReader`).

### Subscriber Side (Sync)
- **Pattern**: **Projection Writer**.
- **Scope**: Used by event subscribers to update projections.
- **Injection**: Injected with `PrimaryDataSource` (projections live in the same database, separate schema).
- **Naming**: `[ProjectionName]Writer`.

## 3. Implementation Rules
- **Tenant Isolation**: Every Repository and Reader MUST filter by `tenant_id`.
- **Transaction Management**: 
    - Transactions on the Command side MUST be managed against the `PrimaryDataSource`.
    - Projection updates by subscribers SHOULD participate in the same local transaction as the inbox checkpoint for atomicity.
