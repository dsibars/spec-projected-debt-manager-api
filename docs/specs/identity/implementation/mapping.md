# Implementation: Identity Module Mapping

Technical mapping for Identity and Access Management (IAM) data. Inherits [[specs/shared/implementation]].
Target Database: **WRITE_DB**

## Module-Specific Skill Assignments

- **Persistence**: `@shared/skills/persistence/postgresql`.
- **Security**:
    - `@shared/skills/security/jwt`
    - `@shared/skills/security/hashing` (Using Argon2id)

## Data Mapping

### Table: `users` (Schema: `identity`)
- `id`: `UUID` (PK)
- `shard_id`: `UUID` (FK -> `identity.shards.id`)
- `email`: `VARCHAR(255)` (Unique, Not Null)
- `is_active`: `BOOLEAN` (Not Null, Default True)
- `last_login_at`: `TIMESTAMP WITH TIME ZONE`
- `created_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)
- `updated_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)

### Table: `credentials` (Schema: `identity`)
- `id`: `UUID` (PK)
- `user_id`: `UUID` (FK -> `identity.users.id`)
- `provider`: `VARCHAR(50)` (Not Null)
- `secret`: `TEXT` (Not Null)
- `provider_data`: `JSONB`

### Table: `shards` (Schema: `identity`)
- `id`: `UUID` (PK)
- `name`: `VARCHAR(255)` (Not Null)
- `status`: `VARCHAR(50)` (Not Null)
- `capacity`: `INTEGER` (Not Null)
- `current_load`: `INTEGER` (Not Null, Default 0)
- `region`: `VARCHAR(100)` (Not Null)

## Indexes
- `idx_users_email` on `identity.users(email)`
- `idx_users_shard_id` on `identity.users(shard_id)`
- `idx_shards_status` on `identity.shards(status)`
- `idx_credentials_user_id` on `identity.credentials(user_id)`
