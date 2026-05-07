# Subscriber: On Identity Purge Requested

## Goal
To delete all financial records for a user who has requested data deletion.

## Subscribes To
- `identity.IdentityPurgeRequested`

## Flow
1. Receive **`EventEnvelope`**. Extract `targetUserId`.
2. Hard-delete all records from:
   - `debts` table where `tenant_id = targetUserId`.
   - Delete all [[projections/DebtSummaryProjection]] for the `targetUserId`.
   - `person_read_models` table where `tenant_id = targetUserId`.
3. Emit **`ModuleDataPurged`** event (payload: `moduleId: "debts", userId: targetUserId`).
4. Emit `DebtsPurged` event.
