# Event Handler: Sync Debt

## Goal
To maintain a local Read Model of Debts within the Payments module, ensuring high availability when validating new payments.

## Subscribes To
- `debts.DebtRegistered`
- `debts.DebtSettled`

## Flow (DebtRegistered)
1. Receive event payload: `id`, `currentBalance`.
2. Create the [[models/DebtReadModel]] with the provided `id` and `currentBalance`.
3. Set `isSettled` to `false`.
4. Persist to the local store.

## Flow (DebtSettled)
1. Receive event payload: `id`.
2. Find the [[models/DebtReadModel]] by `id`.
3. Set `isSettled` to `true`.
4. Persist to the local store.
