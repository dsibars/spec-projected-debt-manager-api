# Command: Apply Payment to Debt

## Goal
To reduce the balance of a debt aggregate when a payment is confirmed.

## Input
- `debtId`: UUID
- `amount`: Integer

## Flow
1. Find the [[models/Debt]] by `debtId`.
2. If not found, abort with `DebtNotFound` error.
3. If `amount` > `currentBalance`, abort with `PaymentExceedsBalance` error.
4. Subtract `amount` from `currentBalance`.
5. If `currentBalance` <= 0, set `isSettled` to `true`.
6. Set `updatedAt` to now.
7. Persist [[models/Debt]].
8. Update [[projections/DebtSummaryProjection]] via [[commands/UpdateDebtSummary]] with:
   - `debtId`: The `debtId`.
   - `remainingAmount`: The new `currentBalance`.
   - `isSettled`: The new `isSettled`.

## Emits
- `DebtSettled` (if newly settled) — see [[events/DebtSettled]]

## Errors
- `DebtNotFound`: If the debt does not exist.
- `InvalidPaymentAmount`: If `amount` is less than or equal to zero.
- `PaymentExceedsBalance`: If the payment amount is greater than the current balance.
