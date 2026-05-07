# Subscriber: On Identity Purge Requested

## Goal
To delete all financial records for a user who has requested data deletion.

## Subscribes To
- `identity.IdentityPurgeRequested`

## Flow
1. Receive **`EventEnvelope`**. Extract `targetUserId`.
2. Hard-delete all records from:
   - `debts` table where `tenant_id = targetUserId`.
   - `debt_summary_projections` table where `tenant_id = targetUserId`.
   - `person_read_models` table where `tenant_id = targetUserId`.
3. Emit `DebtsPurged` event.
