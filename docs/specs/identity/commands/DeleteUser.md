# Command: Delete User

## Goal
Permanently remove a user's identity and trigger the global data purge (GDPR).

## Input
- `userId`: `uuid`

## Preconditions
- The referenced user must exist.

## Flow
1. Find the [[models/User]] by `userId`.
2. If not found, raise `UserNotFound`.
3. Decrement `currentLoad` on the associated [[models/Shard]].
4. Delete all [[models/Credential]] associated with the `userId`.
5. Delete the [[models/User]] record.
6. Delete the [[models/UserIndex]] entry for this user's email.
7. Emit `IdentityPurgeRequested` event.

## Postconditions
- The user and their credentials no longer exist.
- The shard load is decremented.

## Effects
- Emits: `IdentityPurgeRequested` (payload: `targetUserId: userId`)

## Errors
- `UserNotFound`

## Result
- `void`
