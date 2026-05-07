# Skill: Persistence - Repository & DataSource Strategy

This skill defines how the Builder should generate the data access layer for a sharded, split-DB architecture.

## 1. DataSource Strategy
The application MUST maintain two distinct connection pools:
- **`WriteDataSource`**: Points to the `db-write` instance. Used for all state-changing operations on Aggregates.
- **`ReadDataSource`**: Points to the `db-read` instance. Used for all Projections, Read Models, and UI Queries.

## 2. Structural Patterns

### Command Side (Write)
- **Pattern**: **Aggregate Repository**.
- **Scope**: One repository per Aggregate Root defined in `models/`.
- **Injection**: MUST be injected with the `WriteDataSource`.
- **Naming**: `[EntityName]Repository` (e.g., `DebtRepository`, `PersonRepository`).

### Query Side (Read)
- **Pattern**: **Data Access Object (DAO) / Reader**.
- **Scope**: One reader per Projection or Read Model defined in `projections/`.
- **Injection**: MUST be injected with the `ReadDataSource`.
- **Naming**: `[ProjectionName]Reader` (e.g., `DebtSummaryReader`, `PersonReadModelReader`).

### Subscriber Side (Sync)
- **Pattern**: **Projection Writer**.
- **Scope**: Used by event subscribers to update the `READ_DB`.
- **Injection**: MUST be injected with the `ReadDataSource` (with Write permissions).
- **Naming**: `[ProjectionName]Writer`.

## 3. Implementation Rules
- **No Cross-Pollution**: A Repository MUST NOT accept or use the `ReadDataSource`.
- **Transaction Management**: 
    - Transactions on the Command side MUST be managed against the `WriteDataSource`.
    - Transactions on the Subscriber side (for atomic projection updates) MUST be managed against the `ReadDataSource`.
