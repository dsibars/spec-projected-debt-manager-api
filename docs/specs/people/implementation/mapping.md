# Implementation: People Module Mapping

This document maps the business specifications of the People module to the technical Shared Skills. It inherits global baselines from [[specs/shared/implementation]].

## Module-Specific Skill Assignments

- **Persistence**: Use `@shared/skills/persistence/postgresql` for storing [[models/Person]] data.
- **API Routing**: The routes defined in `presentation/rest/api.md` should be implemented using the platform defined in the implementation `config` (e.g., Spring, Ktor, Axum, Gin).

## Data Mapping

- **Table**: `people` (Schema: `people`)
- **Columns**:
    - `id`: `UUID` (Primary Key)
    - `tenant_id`: `UUID` (Not Null)
    - `name`: `VARCHAR(255)` (Not Null)
    - `email`: `VARCHAR(255)` (Unique, Nullable)
    - `phone`: `VARCHAR(50)` (Nullable)
    - `is_archived`: `BOOLEAN` (Not Null, Default False)
    - `created_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)
    - `updated_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)
    - `version`: `INTEGER` (Not Null, Default 0)

