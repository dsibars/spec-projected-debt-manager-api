# Implementation: Debts Module Mapping

Technical mapping for financial data. Inherits [[specs/shared/implementation]].

## 1. Write Database (`WRITE_DB`)
Target: **Aggregates Only**

### Table: `debts` (Schema: `debts`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `person_id`: `UUID` (Not Null - No Physical FK)
- `name`: `VARCHAR(255)`
- `total_amount`: `BIGINT`
- `current_balance`: `BIGINT`
- `currency`: `VARCHAR(3)` (Default: 'USD')
- `direction`: `VARCHAR(20)` (Not Null)
- `due_date`: `TIMESTAMP WITH TIME ZONE` (Nullable)
- `is_archived`: `BOOLEAN` (Not Null, Default False)
- `version`: `INTEGER` (Not Null, Default 0)
- `created_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)
- `updated_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)

### Table: `payments` (Schema: `debts`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `debt_id`: `UUID` (Not Null)
- `amount`: `BIGINT` (Not Null)
- `notes`: `TEXT` (Nullable)
- `paid_at`: `TIMESTAMP WITH TIME ZONE` (Nullable)
- `created_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)

---

## 2. Read Database (`READ_DB`)
Target: **All Projections**

### Table: `debt_summary_projections` (Schema: `debts`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `person_id`: `UUID` (Not Null)
- `person_name`: `VARCHAR(255)`
- `debt_name`: `VARCHAR(255)`
- `total_amount`: `BIGINT`
- `remaining_amount`: `BIGINT`
- `payment_count`: `INTEGER` (Not Null, Default 0)
- `last_payment_date`: `TIMESTAMP WITH TIME ZONE` (Nullable)
- `is_settled`: `BOOLEAN` (Not Null, Default False)
- `is_archived`: `BOOLEAN` (Not Null, Default False)
- `updated_at`: `TIMESTAMP WITH TIME ZONE` (Not Null)

### Table: `person_read_models` (Schema: `debts`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `name`: `VARCHAR(255)`
- `is_archived`: `BOOLEAN`

## Indexes
- `WRITE_DB`: `idx_debts_tenant` on `debts(tenant_id)`
- `WRITE_DB`: `idx_payments_debt` on `payments(debt_id)`
- `READ_DB`: `idx_debt_summary_tenant` on `debt_summary_projections(tenant_id)`
- `READ_DB`: `idx_person_read_tenant` on `person_read_models(tenant_id)`
