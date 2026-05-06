# Implementation: Payments Module Mapping

Technical mapping for payment data. Inherits [[specs/shared/implementation]].

## Module-Specific Skill Assignments

- **Persistence**: `@shared/skills/persistence/postgresql`.

## Data Mapping

### Table: `payments` (Schema: `payments`)
- `id`: `UUID` (PK)
- `debt_id`: `UUID` (FK -> `debts.debts.id`)
- `amount`: `BIGINT` (Cents)
- `notes`: `TEXT`
- `paid_at`: `TIMESTAMP WITH TIME ZONE`
- `created_at`: `TIMESTAMP`

## Indexes
- `idx_payments_debt_id` on `payments(debt_id)`
- `idx_payments_paid_at` on `payments(paid_at)`
