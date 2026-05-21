# Subscriber: On Debt Deleted

## Goal
Update the DebtSummaryProjection when a debt is archived.

## Subscribes To
- `debts.DebtDeleted`

## Flow
1. Receive **`EventEnvelope`**.
2. Extract `debtId` from payload.
3. Find the [[projections/DebtSummaryProjection]] by `debtId`.
4. Set `isArchived` to `true`.
5. Set `updatedAt` to now.
6. Persist to the projection store.

## Result
- `void`
