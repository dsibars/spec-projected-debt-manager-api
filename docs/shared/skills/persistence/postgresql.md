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
    *   **Strictly Banned**: Using ORM features like `ddl-auto=update` or `hibernate.hbm2ddl.auto` is strictly forbidden.
    *   **Mandatory Tooling**: Schema changes must be declarative, explicitly versioned SQL migrations managed by an enterprise migration tool (e.g., Flyway/Liquibase for Java, `golang-migrate` for Go).
    *   **Format**: All migrations targeting PostgreSQL must be written in **Pure SQL**. Abstract formats (e.g., Liquibase XML/YAML) are forbidden to ensure raw visibility and universal readability.
    *   **Naming Convention**: All SQL migration scripts must adhere to the standard `V[Version]__[Description].sql` naming format (e.g., `V1__create_people_table.sql`).
    *   **Execution Strategy**: Migrations must be executed automatically on application startup by the native framework integration (e.g., Spring Boot + Flyway auto-configure), or orchestrated via a pre-deployment CI/CD pipeline step (e.g., a `make migrate` task). This guarantees the schema is ready before the application accepts traffic.
    *   **Explicit Indexing**: The Builder MUST generate explicit `CREATE INDEX` scripts for any fields that are used in Domain Repository lookup queries (e.g., `email`, `person_id`).
*   **Naming Convention**: `snake_case` for tables and columns.
*   **Primary Keys**: UUID (v4) or ULID are required.
*   **Concurrency Law (Optimistic Locking)**: To prevent race conditions during "Read-Validate-Write" flows (e.g., balance updates), the Builder MUST implement **Optimistic Locking** for all domain entities. Every table MUST include a `version` column (integer), and updates MUST increment this version and fail if the version in the database has changed since the entity was loaded.

## Integration Patterns

*   **Repository Implementation**: Use the native database driver or a lightweight query builder for the target language.
*   **Connection Pooling**: Must be implemented in all production-ready projections. Standard configuration:
    *   `max_pool_size`: 10 (default).
    *   `min_idle`: 2.
*   **Transactions**: Logic layers requiring atomicity must be wrapped in technical transactions at the infrastructure layer.

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Write Aggregates**: `db.write.url`, `db.write.user`, `db.write.password`
- **Read Projections**: `db.read.url`, `db.read.user`, `db.read.password`

## Connection Credentials (Local Dev)
- **Host**: `localhost` (or `db-write`/`db-read` within docker-compose).
- **Port**: `5432` / `5433`.
- **User**: `spd_user`.
- **Password**: `spd_pass`.