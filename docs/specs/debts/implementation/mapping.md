# Implementation: Debts Module Mapping

Technical mapping for financial data. Inherits [[specs/shared/implementation]].

## Module-Specific Skill Assignments

- **Persistence**: `@shared/skills/persistence/postgresql`.

## Data Mapping

### Table: `debts` (Schema: `debts`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `person_id`: `UUID` (FK -> `debts.person_read_model.id`)
- `name`: `VARCHAR(255)`
- `total_amount`: `BIGINT` (Cents)
- `current_balance`: `BIGINT` (Cents)
- `currency`: `CHAR(3)`
- `direction`: `VARCHAR(20)`
- `due_date`: `TIMESTAMP WITH TIME ZONE`
- `is_settled`: `BOOLEAN`
- `is_archived`: `BOOLEAN` (Default False)
- `created_at`: `TIMESTAMP WITH TIME ZONE`
- `updated_at`: `TIMESTAMP WITH TIME ZONE`
- `version`: `INTEGER` (Not Null, Default 0)



## Indexes
- `idx_debts_person_id` on `debts(person_id)`
- `idx_debts_is_settled` on `debts(is_settled)`
- `idx_debts_is_archived` on `debts(is_archived)`
