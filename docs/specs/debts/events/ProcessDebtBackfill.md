# Event Handler: Process Debt Backfill Request

## Goal
To serve historical Debt data to new subscribers by replaying Domain Events.

## Subscribes To
- `payments.RequestDebtBackfill`

## Flow
1. Receive event payload.
2. Query the Store for all [[models/Debt]] records.
3. For each record, emit a `DebtRegistered` event with the debt's `id`, `totalAmount`, and `currentBalance`.
4. If the debt `isSettled` is true, also emit a `DebtSettled` event immediately after.
