# Command: Register User

## Goal
Create a new identity and assign it to an available shard.

## Input
- `email`: `string`
- `password`: `string?`
- `provider`: `LOCAL | GOOGLE | APPLE`
- `externalId`: `string?`

## Flow
1. **Global Uniqueness Check**:
   - Query the [[models/UserIndex]] in the Global DB to ensure `email` is not already registered.
   - If found, return `EmailAlreadyInUse`.
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
8. Return `userId`.

## Emits
- `UserRegistered` (payload: `userId`, `shardId`, `email`)
