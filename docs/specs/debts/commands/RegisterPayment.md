# Command: Register Payment

## Goal
Record a payment event for a specific debt and synchronously update the debt's balance.

## Input
- `tenantId`: UUID (Injected from Auth context)
- `debtId`: UUID
- `amount`: Integer
- `notes`: String?

## Preconditions
- `amount` must be greater than 0.
- `amount` must be less than or equal to the debt's current balance.
- The referenced debt must exist and not be archived.

## Flow
1. Find the local [[models/Debt]] aggregate using `debtId`.
2. If not found, raise `DebtNotFound`.
3. Subtract `amount` from `debt.currentBalance`.
4. Generate a unique `id` for the [[models/Payment]].
5. Set `createdAt` to current time.
6. Store the new [[models/Payment]] AND persist the updated [[models/Debt]] within the same transaction.
7. If `debt.currentBalance` reached 0, emit `DebtSettled`.
8. Emit `PaymentRegistered`.

## Postconditions
- A `Payment` record exists linked to the debt.
- The debt's `currentBalance` is reduced by `amount`.
- If balance reached 0, the debt is in settled state.

## Effects
- Emits: [[events/PaymentRegistered]]
- Emits: [[events/DebtSettled]] (conditional, if balance reaches 0)

## Errors
- `DebtNotFound`: Referenced debt does not exist.
- `InvalidAmount`: Amount is non-positive.
- `PaymentExceedsBalance`: Paying more than the remaining debt balance.

## Result
- `id`: UUID of the created payment.
