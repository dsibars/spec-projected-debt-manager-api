# Implementation: Identity Module Mapping

Technical mapping for Identity and Access Management (IAM) data. Inherits [[specs/shared/implementation]].

## Module-Specific Skill Assignments

- **Persistence**: `@shared/skills/persistence/postgresql`.
- **Security**:
    - `@shared/skills/security/jwt`
    - `@shared/skills/security/hashing` (Using Argon2id)

## Data Mapping

### Table: `users` (Schema: `identity`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (FK -> `identity.tenants.id`)
- `email`: `VARCHAR(255)` (Unique, Not Null)
- `password_hash`: `VARCHAR(255)` (Not Null)
- `created_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)
- `updated_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)
- `version`: `INTEGER` (Not Null, Default 0)

### Table: `tenants` (Schema: `identity`)
- `id`: `UUID` (PK)
- `name`: `VARCHAR(255)` (Not Null)
- `capacity`: `INTEGER` (Not Null)
- `current_load`: `INTEGER` (Not Null, Default 0)
- `is_active`: `BOOLEAN` (Not Null, Default True)
- `created_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)
- `updated_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)
- `version`: `INTEGER` (Not Null, Default 0)

## Indexes
- `idx_users_email` on `identity.users(email)`
- `idx_users_tenant_id` on `identity.users(tenant_id)`
- `idx_tenants_is_active` on `identity.tenants(is_active)`
