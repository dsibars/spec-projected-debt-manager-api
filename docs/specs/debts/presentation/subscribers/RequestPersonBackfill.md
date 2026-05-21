# Command: Request Person Backfill

## Goal
Trigger the hydration of the local `PersonReadModel` upon initial deployment or catastrophic data loss.

## Input
- (None)

## Preconditions
- The local `PersonReadModel` store is completely empty.

## Flow
1. Executed automatically on application startup if the local Person read model store is completely empty.
2. Generate a backfill request.
3. Emit the request to the central bus.

## Postconditions
- A backfill request has been emitted.

## Effects
- Emits: `RequestPersonBackfill` (payload: empty)

## Errors
- (None)

## Result
- `void`
