# Skill: Persistence - PostgreSQL

## Category: persistence
## Provides:
- Postgresql
## Conflicts With:
- migrations
- read-write-split
- repository-pattern
- seeding
## Depends On:
- None explicitly declared


This skill defines the technical "laws" for persisting data using a PostgreSQL database.

## Technical Requirements

*   **Database Engine**: PostgreSQL 16+.
*   **Database Name**: `spd_debt_manager`.
*   **Bootstrapping & Seeding**: The implementation MUST follow the laws defined in `@shared/skills/persistence/seeding`. At least one active tenant must be seeded for the system to be functional.
*   **Schema Strategy**: Per-module isolation. Each module in `docs/specs/[module]` maps to a dedicated schema in the database (e.g., `people`, `debts`).
*   **Migrations & DDL**:
    *   **Strictly Banned**: Using ORM auto-migration features (e.g., Hibernate `ddl-auto`, Diesel `setup`, Prisma `migrate dev`) is strictly forbidden. Schema generation must be disabled in all frameworks.
    *   **Mandatory Tooling**: Schema changes must be declarative, explicitly versioned SQL migrations managed by a migration tool. Each implementation stack MUST use its canonical tool:
        *   **Java / Kotlin**: Flyway or Liquibase.
        *   **Go**: `golang-migrate` (`migrate` CLI).
        *   **Rust**: `sqlx migrate` (from the `sqlx-cli` crate) or `refinery`.
    *   **Format**: All migrations targeting PostgreSQL must be written in **Pure SQL**. Abstract formats (e.g., Liquibase XML/YAML) are forbidden to ensure raw visibility and universal readability.
    *   **Naming Convention**: All SQL migration scripts must adhere to the standard `V[Version]__[Description].sql` naming format (e.g., `V1__create_people_table.sql`).
    *   **Execution Strategy**: Migrations must be executed automatically on application startup by the native framework integration (e.g., Spring Boot + Flyway auto-configure, Ktor + Flyway plugin, Axum/Gin via `sqlx migrate run` or `migrate` CLI in `make start-core`), or orchestrated via a pre-deployment CI/CD pipeline step (e.g., a `make migrate` task). This guarantees the schema is ready before the application accepts traffic.
    *   **Explicit Indexing**: The Builder MUST generate explicit `CREATE INDEX` scripts for any fields that are used in Domain Repository lookup queries (e.g., `email`, `person_id`).
*   **Naming Convention**: `snake_case` for tables and columns.
*   **Primary Keys**: UUID (v4) or ULID are required.
*   **Concurrency Law (Optimistic Locking)**: To prevent race conditions during "Read-Validate-Write" flows (e.g., balance updates), the Builder MUST implement **Optimistic Locking** for all domain entities. Every table MUST include a `version` column (integer), and updates MUST increment this version and fail if the version in the database has changed since the entity was loaded.

## Integration Patterns

*   **Repository Implementation**: Use the native database driver or a lightweight query builder for the target language. The Builder MUST NOT use heavy ORM features that bypass explicit SQL control for write operations.
*   **Connection Pooling**: Must be implemented in all production-ready projections. The connection pool is the single shared resource for all repository and reader operations.
    *   `max_pool_size`: 10 (default).
    *   `min_idle`: 2.
*   **Transactions**: Logic layers requiring atomicity must be wrapped in technical transactions at the infrastructure layer. The transaction scope MUST cover the Command execution, event emission to the Outbox, and Inbox checkpoint writes.

## Per-Stack Integration Matrix

| Concern | Java / Spring | Kotlin / Ktor | Go / Gin | Rust / Axum |
|---|---|---|---|---|
| **Migration Tool** | Flyway / Liquibase | Flyway plugin | `golang-migrate` | `sqlx migrate` / `refinery` |
| **DB Driver / Pool** | HikariCP (via Spring Boot) | HikariCP | `pgxpool` (`pgx/v5`) | `sqlx` + `deadpool-postgres` |
| **Query Builder** | JPA / jOOQ / raw JDBC | Exposed / jOOQ / raw JDBC | Raw SQL + `pgx` | `sqlx` (`query_as!`) / `tokio-postgres` |
| **Transaction Mgmt** | `@Transactional` | Manual or Exposed DSL | `pgx.Tx` | `sqlx::Transaction` |
| **Migration Exec** | Auto on boot | Auto on boot | `make migrate` or `Init()` | `sqlx migrate run` in `make start-core` |

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Primary Database**: `db.primary.url`, `db.primary.user`, `db.primary.password`
- **Read Replica** (optional): `db.replica.url`, `db.replica.user`, `db.replica.password`

## Connection Credentials (Local Dev)
- **Host**: `localhost` (or `postgres` within docker-compose).
- **Port**: `5432`.
- **User**: `spd_user`.
- **Password**: `spd_pass`.
