# Skill: Persistence - Repository & Connection Pool Strategy

## Category: persistence
## Provides:
- Repository Pattern
## Conflicts With:
- None
## Depends On:
- @shared/skills/persistence/postgresql

This skill defines how the Builder should generate the data access layer for a multi-tenant SaaS architecture.

## 1. Connection Pool Strategy
The application MUST maintain distinct connection pools:
- **`PrimaryPool`**: Points to the primary PostgreSQL instance. Used for all write operations on Aggregates and for projections co-located in the same database.
- **`ReplicaPool`** (Optional): Points to PostgreSQL read replicas. Used for Query operations on read-heavy projections.

The Builder MUST NOT leak connection pool details into the domain or application layers. The pool is injected into infrastructure-layer constructors only.

## 2. Structural Patterns

### Command Side (Write)
- **Pattern**: **Aggregate Repository**.
- **Scope**: One repository per Aggregate Root defined in `models/`.
- **Injection**: MUST be injected with the `PrimaryPool`.
- **Naming**: `[EntityName]Repository` (e.g., `DebtRepository`, `PersonRepository`).
- **Responsibility**: Load aggregates by ID, persist new aggregates, update existing aggregates with optimistic locking.

### Query Side (Read)
- **Pattern**: **Data Access Object (DAO) / Reader**.
- **Scope**: One reader per Projection or Read Model defined in `projections/`.
- **Injection**: Injected with `PrimaryPool` or `ReplicaPool`.
- **Naming**: `[ProjectionName]Reader` (e.g., `DebtSummaryReader`, `PersonReadModelReader`).
- **Responsibility**: Execute read-only SQL queries returning DTOs or raw tuples. No domain logic.

### Subscriber Side (Sync)
- **Pattern**: **Projection Writer**.
- **Scope**: Used by event subscribers to update projections.
- **Injection**: Injected with `PrimaryPool` (projections live in the same database, separate schema).
- **Naming**: `[ProjectionName]Writer`.
- **Responsibility**: Upsert or delete projection rows based on event payload.

## 3. Implementation Rules
- **Tenant Isolation**: Every Repository, Reader, and Writer MUST filter by `tenant_id`. The `tenant_id` is derived from the execution context (JWT `sub` claim for HTTP, `EventEnvelope.metadata.tenantId` for subscribers).
- **Transaction Management**:
    - Transactions on the Command side MUST be managed against the `PrimaryPool`.
    - Projection updates by subscribers SHOULD participate in the same local transaction as the inbox checkpoint for atomicity.
    - The transaction boundary MUST wrap: (1) Aggregate persistence, (2) Outbox event insertion.
- **No ORM Leakage**: Domain models MUST NOT carry ORM annotations or framework-specific metadata. Mapping between domain aggregates and database rows happens in the repository implementation layer.

## 4. Per-Stack Repository Mapping

| Concern | Java / Spring | Kotlin / Ktor | Go / Gin | Rust / Axum |
|---|---|---|---|---|
| **Repository Interface** | `interface DebtRepository` extending `JpaRepository` or custom | `interface DebtRepository` | `type DebtRepository interface` | `trait DebtRepository` |
| **Implementation** | `JpaDebtRepository` or raw JDBC | `ExposedDebtRepository` or raw JDBC | `PostgresDebtRepository` (struct) | `PostgresDebtRepository` (struct impl trait) |
| **DI Pattern** | Constructor injection | Koin / manual DI | Interface injection / factory | `Arc<dyn Trait>` via `axum::extract::State` |
| **Connection Source** | `EntityManager` / `JdbcTemplate` | `Database` (Exposed) / `Connection` | `*pgxpool.Pool` | `Pool<Postgres>` (deadpool) |
| **Optimistic Locking** | `@Version` or manual `WHERE version = ?` | Manual or Exposed DSL | Manual `UPDATE ... WHERE version = $1` | Manual `UPDATE ... WHERE version = $1` |
