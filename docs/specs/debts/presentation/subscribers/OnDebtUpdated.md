# Subscriber: On Debt Updated

## Goal
Update the DebtSummaryProjection when a debt's metadata changes.

## Subscribes To
- `debts.DebtUpdated`

## Flow
1. Receive **`EventEnvelope`**.
2. Extract `debtId` and updated fields (`name`, `dueDate`) from payload.
3. Find the [[projections/DebtSummaryProjection]] by `debtId`.
4. If `name` is provided, update `debtName`.
5. Set `updatedAt` to now.
6. Persist to the projection store.

## Result
- `void`
