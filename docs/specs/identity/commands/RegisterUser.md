# Command: Register User

## Goal
Create a new identity and assign it to an available shard.

## Input
- `email`: `string`
- `password`: `string?`
- `provider`: `LOCAL | GOOGLE | APPLE`
- `externalId`: `string?`

## Flow
1. Validate that `email` is not already in use.
2. **Shard Assignment**:
   - Find an `ACTIVE` [[models/Shard]] with capacity.
   - Selected Shard ID becomes the `shardId` for this user.
3. Generate a unique `userId`.
4. Create the [[models/User]] record with the assigned `shardId`.
5. Increment `currentLoad` on the selected [[models/Shard]].
6. ... (Password hashing / Credential creation) ...
7. Emit `UserRegistered` event.
8. Return `userId`.

## Emits
- `UserRegistered` (payload: `userId`, `shardId`, `email`)
