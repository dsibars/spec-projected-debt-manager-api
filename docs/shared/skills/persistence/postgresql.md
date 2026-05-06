# Skill: Persistence - PostgreSQL

This skill defines the technical "laws" for persisting data using a PostgreSQL database.

## Technical Requirements

*   **Database Engine**: PostgreSQL 16+.
*   **Database Name**: `spd_debt_manager`.
*   **Schema Strategy**: Per-module isolation. Each module in `docs/specs/[module]` maps to a dedicated schema in the database (e.g., `people`, `debts`).
*   **Migrations**: All schema changes must be declarative and versioned (referencing `docs/specs/[module]/migrations`).
*   **Naming Convention**: `snake_case` for tables and columns.
*   **Primary Keys**: UUID (v4) or ULID are required.

## Integration Patterns

*   **Repository Implementation**: Use the native database driver or a lightweight query builder for the target language.
*   **Connection Pooling**: Must be implemented in all production-ready projections. Standard configuration:
    *   `max_pool_size`: 10 (default).
    *   `min_idle`: 2.
*   **Transactions**: Logic layers requiring atomicity must be wrapped in technical transactions at the infrastructure layer.

## Connection Credentials (Local Dev)
- **Host**: `localhost` (or `db` within docker-compose network).
- **Port**: `5432`.
- **User**: `spd_user`.
- **Password**: `spd_pass`.
