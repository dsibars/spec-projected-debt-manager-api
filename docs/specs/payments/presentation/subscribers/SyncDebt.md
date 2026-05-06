# Subscriber: Sync Debt

## Goal
To adapt incoming Debt events to the local synchronization command.

## Subscribes To
- `debts.DebtRegistered`
- `debts.DebtSettled`

## Adapts
- [[../../commands/UpdateDebtReadModel]]

## Flow (DebtRegistered)
1. Receive **`EventEnvelope`**. Extract `id` and `currentBalance` from `data`.
2. Call [[../../commands/UpdateDebtReadModel]] with `id`, `currentBalance`, and `isSettled: false`.

## Flow (DebtSettled)
1. Receive **`EventEnvelope`**. Extract `id` from `data`.
2. Call [[../../commands/UpdateDebtReadModel]] with `id` and `isSettled: true`.
