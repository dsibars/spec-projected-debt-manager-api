# Implementation: Payments Module Mapping

Technical mapping for payment data. Inherits [[specs/shared/implementation]].

## 1. Write Database (`WRITE_DB`)
Target: **Aggregates Only**

### Table: `payments` (Schema: `payments`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `debt_id`: `UUID` (Not Null - No Physical FK)
- `amount`: `BIGINT`
- `notes`: `TEXT`
- `paid_at`: `TIMESTAMP WITH TIME ZONE`
- `version`: `INTEGER`

---

## 2. Read Database (`READ_DB`)
Target: **All Projections**

### Table: `debt_read_models` (Schema: `payments`)
- `id`: `UUID` (PK)
- `tenant_id`: `UUID` (Not Null)
- `current_balance`: `BIGINT`
- `is_settled`: `BOOLEAN`

## Indexes
- `WRITE_DB`: `idx_payments_tenant` on `payments(tenant_id)`
- `READ_DB`: `idx_debt_read_tenant` on `debt_read_models(tenant_id)`
