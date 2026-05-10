# Command: Update Person Read Model

## Goal
To maintain a local projection of Person data within the Debts context.

## Input
- `id`: `uuid`
- `name`: `string?`
- `isArchived`: `boolean`

## Flow
1. Find the [[projections/PersonReadModel]] by `id`.
2. If not found:
   - Otherwise, create a new record.
3. Update `name` if provided.
4. Set `isArchived` to the provided value.
5. Persist to the store.

## Emits
- None
