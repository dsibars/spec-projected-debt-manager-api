# Subscriber: On Payment Registered

## Goal
Update the DebtSummaryProjection when a payment is registered for a debt.

## Subscribes To
- `debts.PaymentRegistered`

## Flow
1. Receive **`EventEnvelope`**.
2. Extract `debtId`, `amount`, `paidAt` from payload.
3. Find the [[projections/DebtSummaryProjection]] by `debtId`.
4. Decrement `remainingAmount` by `amount`.
5. Increment `paymentCount` by 1.
6. Update `lastPaymentDate` to `paidAt`.
7. If `remainingAmount` reaches 0, set `isSettled` to `true`.
8. Set `updatedAt` to now.
9. Persist to the projection store.

## Result
- `void`
