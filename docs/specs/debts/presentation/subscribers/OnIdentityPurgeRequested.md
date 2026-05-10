# Subscriber: On Identity Purge Requested

## Goal
To delete all financial records for a user who has requested data deletion.

## Subscribes To
- `identity.IdentityPurgeRequested`

## Flow
1. Receive **`EventEnvelope`**. Extract `targetUserId`.
2. Hard-delete all records from:
   - All Debt aggregates where `tenantId = targetUserId`.
   - Delete all [[projections/DebtSummaryProjection]] for the `targetUserId`.
   - All Person read models where `tenantId = targetUserId`.
3. Emit **`ModuleDataPurged`** event (payload: `moduleId: "debts", userId: targetUserId`).
4. Emit `DebtsPurged` event.
