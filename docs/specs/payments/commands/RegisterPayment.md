# Use Case: Register Payment

## Goal
To record a payment event for a specific debt.

## Flow
1. Receive `debtId`, `amount`, and optional `notes`.
2. Find the local [[models/DebtReadModel]] using `debtId`.
3. If not found, return `DebtNotFound`.
4. Validate that `amount` > 0.
5. Validate that `isSettled` is `false`. If true, return `DebtSettled`.
6. Validate that `amount` <= `debt.currentBalance`.
7. Generate a unique `id` for the [[models/Payment]].
8. Set `createdAt` to current time.
9. Store the new [[models/Payment]].
10. Return the registered `id`.

## Emits
- `PaymentRegistered` (payload: `id`, `debtId`, `amount`)

## Errors
- `DebtNotFound`: Referenced debt does not exist in the local read model.
- `DebtSettled`: Cannot pay against a settled debt.
- `InvalidAmount`: Amount is non-positive.
- `PaymentExceedsBalance`: Paying more than the remaining debt balance.
