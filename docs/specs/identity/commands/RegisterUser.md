# Command: Register User

## Goal
Create a new identity and assign it to an available shard.

## Input
- `email`: `string`
- `password`: `string?`
- `provider`: `LOCAL | GOOGLE | APPLE`
- `externalId`: `string?`

## Preconditions
- The `email` must not already be registered in the global `UserIndex`.

## Flow
1. **Global Uniqueness Check**:
   - Query the [[models/UserIndex]] in the Global DB to ensure `email` is not already registered.
   - If found, raise `EmailAlreadyInUse`.
2. **Shard Assignment**:
   - Find an `ACTIVE` [[models/Shard]] with capacity (Atomic increment).
   - Selected Shard ID becomes the `shardId` for this user.
3. Generate a unique `userId`.
4. **Local Creation**:
   - Create the [[models/User]] record in the selected Shard DB.
5. **Credential Creation**:
   - Create a [[models/Credential]] record linked to the `userId`.
6. **Index Finalization**:
   - Create the [[models/UserIndex]] record in the Global DB to lock the email to the generated `userId` and `shardId`.
7. Emit `UserRegistered` event.

## Postconditions
- A `User` aggregate exists in the assigned Shard.
- A `Credential` exists for the user.
- The global `UserIndex` contains the email mapping.

## Effects
- Emits: `UserRegistered` (payload: `userId`, `shardId`, `email`)

## Errors
- `EmailAlreadyInUse`: If `email` is already registered.
- `SystemOverloaded`: If no active shard has available capacity.

## Result
- `userId`: UUID of the created user.
