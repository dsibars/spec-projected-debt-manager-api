# Command: Update Debt Read Model

## Goal
To maintain a local projection of Debt data within the Payments context.

## Input
- `id`: `uuid`
- `currentBalance`: `integer?`
- `isSettled`: `boolean`

## Flow
1. Find the [[models/DebtReadModel]] by `id`.
2. If not found:
   - Create a new [[models/DebtReadModel]].
3. Update `currentBalance` if provided.
4. Set `isSettled` to the provided value.
5. Persist to the store.
