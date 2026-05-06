# Event Handler: Sync Payment

## Goal
To update the core Debt balance when a payment is registered by the Payments module.

## Subscribes To
- `payments.PaymentRegistered`

## Flow
1. Receive event payload: `debtId`, `amount`.
2. Find the [[models/Debt]] by `debtId`.
3. If not found, log a severe data inconsistency error (this should be impossible if foreign constraints or correct ordering are maintained).
4. Subtract `amount` from `currentBalance`.
5. If `currentBalance` <= 0, set `isSettled` to `true`.
6. Set `updatedAt` to now.
7. Persist the updated [[models/Debt]].

## Emits (If Settled)
- `DebtSettled` (payload: `id`)
