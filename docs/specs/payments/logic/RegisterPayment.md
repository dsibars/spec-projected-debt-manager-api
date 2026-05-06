# Use Case: Register Payment

## Goal
To record a payment event and update the corresponding debt's balance across module boundaries.

## Flow
1. Receive `debtId`, `amount`, and optional `notes`.
2. Access the [[specs/debts/models/Debt]] via the `debts` module using `debtId`.
3. If the debt is not found, return `DebtNotFound`.
4. Validate that `amount` > 0.
5. Validate that `amount` <= `debt.currentBalance`.
6. Generate a unique `id` for the [[models/Payment]].
7. Set `paidAt` and `createdAt` to current time.
8. Store the new [[models/Payment]].
9. Trigger a balance update in the `debts` module for [[specs/debts/models/Debt]]:
    - Subtract `amount` from `currentBalance`.
    - If `currentBalance` is 0, set `isSettled` to `true`.
    - Update `updatedAt` to now.
10. Return the registered [[models/Payment]].

## Errors
- `DebtNotFound`: Referenced debt does not exist.
- `InvalidAmount`: Amount is non-positive.
- `PaymentExceedsBalance`: Paying more than the remaining debt balance.
