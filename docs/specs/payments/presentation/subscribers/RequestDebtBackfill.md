# Use Case: Request Debt Backfill

## Goal
To trigger the hydration of the local `DebtReadModel` upon initial deployment of the Payments module.

## Flow
1. Executed automatically on application startup if the local `payments_debts_read_model` table is empty.
2. Generate a backfill request.
3. Emit the request to the central bus.
4. Return success.

## Emits
- `RequestDebtBackfill` (payload: empty)
