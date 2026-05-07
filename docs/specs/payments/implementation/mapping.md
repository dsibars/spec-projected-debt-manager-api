# Implementation: Payments Module Mapping

Technical mapping for payment data. Inherits [[specs/shared/implementation]].

## Module-Specific Skill Assignments

- **Persistence**: `@shared/skills/persistence/postgresql`.

## Data Mapping

### Table: `payments` (Schema: `payments`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `debt_id`: `UUID` (FK -> `payments.debt_read_model.id`)
- `amount`: `BIGINT` (Cents)
- `notes`: `TEXT`
- `paid_at`: `TIMESTAMP WITH TIME ZONE`
- `created_at`: `TIMESTAMP WITH TIME ZONE`
- `updated_at`: `TIMESTAMP WITH TIME ZONE`
- `version`: `INTEGER` (Not Null, Default 0)


## Indexes
- `idx_payments_debt_id` on `payments(debt_id)`
- `idx_payments_paid_at` on `payments(paid_at)`
