# Command: Update Person Read Model

## Goal
Maintain a local projection of Person data within the Debts context.

## Input
- `id`: `uuid`
- `name`: `string?`
- `isArchived`: `boolean`

## Preconditions
- (None; this is an internal projection update command triggered by events.)

## Flow
1. Find the [[projections/PersonReadModel]] by `id`.
2. If not found, create a new record.
3. Update `name` if provided.
4. Set `isArchived` to the provided value.
5. Persist to the store.

## Postconditions
- A `PersonReadModel` exists for this `id` with the latest `name` and `isArchived` state.

## Effects
- (None)

## Errors
- (None)

## Result
- `void`
