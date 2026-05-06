# Use Case: Request Person Backfill

## Goal
To trigger the hydration of the local `PersonReadModel` upon initial deployment or catastrophic data loss.

## Flow
1. Executed automatically on application startup if the local `debts_people_read_model` table is completely empty.
2. Generate a backfill request.
3. Emit the request to the central bus.
4. Return success.

## Emits
- `RequestPersonBackfill` (payload: empty)
