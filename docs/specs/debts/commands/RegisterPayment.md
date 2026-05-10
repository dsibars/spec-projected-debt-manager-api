# Use Case: Register Payment

## Goal
To record a payment event for a specific debt and synchronously update the debt's balance.

## Input
- `tenantId`: UUID (Injected from Auth context)
- `debtId`: UUID
- `amount`: Integer
- `notes`: String?

## Flow
1. Receive `debtId`, `amount`, and optional `notes`.
2. Find the local [[models/Debt]] aggregate using `debtId`.
3. If not found, return `DebtNotFound`.
4. Validate that `amount` > 0.
5. Validate that `amount` <= `debt.currentBalance`.
6. Subtract `amount` from `debt.currentBalance`.
7. Generate a unique `id` for the [[models/Payment]].
8. Set `createdAt` to current time.
9. Store the new [[models/Payment]] AND persist the updated [[models/Debt]] within the same transaction.
10. Return the registered `Payment` `id`.

## Emits
- `PaymentRegistered`
- `DebtSettled` (if `debt.currentBalance` reached 0)

## Errors
- `DebtNotFound`: Referenced debt does not exist.
- `InvalidAmount`: Amount is non-positive.
- `PaymentExceedsBalance`: Paying more than the remaining debt balance.
