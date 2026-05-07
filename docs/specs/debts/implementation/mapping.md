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
- `is_settled`: `BOOLEAN`
- `version`: `INTEGER`

---

## 2. Read Database (`READ_DB`)
Target: **All Projections**

### Table: `debt_summary_projections` (Schema: `debts`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `person_name`: `VARCHAR(255)`
- `debt_name`: `VARCHAR(255)`
- `total_amount`: `BIGINT`
- `remaining_amount`: `BIGINT`
- `is_settled`: `BOOLEAN`

### Table: `person_read_models` (Schema: `debts`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `name`: `VARCHAR(255)`
- `is_archived`: `BOOLEAN`

## Indexes
- `WRITE_DB`: `idx_debts_tenant` on `debts(tenant_id)`
- `READ_DB`: `idx_debt_summary_tenant` on `debt_summary_projections(tenant_id)`
- `READ_DB`: `idx_person_read_tenant` on `person_read_models(tenant_id)`
