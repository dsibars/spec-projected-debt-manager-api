# Command: Apply Payment to Debt

## Goal
To reduce the balance of a debt aggregate when a payment is confirmed.

## Input
- `debtId`: `uuid`
- `amount`: `integer`

## Flow
1. Find the [[models/Debt]] by `debtId`.
2. If not found, log error.
3. Subtract `amount` from `currentBalance`.
4. If `currentBalance` <= 0, set `isSettled` to `true`.
5. Set `updatedAt` to now.
6. Persist [[models/Debt]].

## Emits
- `DebtSettled` (if newly settled)
