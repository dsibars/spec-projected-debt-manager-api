# Command: Delete User

## Goal
To permanently remove a user's identity and trigger the global data purge (GDPR).

## Input
- `userId`: `uuid`

## Flow
1. Find the [[models/User]] by `userId`.
2. Decrement `currentLoad` on the associated [[models/Shard]].
3. Delete all [[models/Credential]] associated with the `userId`.
4. Delete the [[models/User]] record.
5. Emit **`IdentityPurgeRequested`** event.

## Emits
- `IdentityPurgeRequested` (payload: `targetUserId: userId`)
