# Command: Register User

## Goal
Create a new identity and its initial credentials.

## Input
- `email`: `string`
- `password`: `string?`
- `provider`: `LOCAL | GOOGLE | APPLE`
- `externalId`: `string?` (For OAuth)

## Flow
1. Validate that `email` is not already in use.
2. Generate a unique `userId`.
3. Create the [[models/User]] record.
4. If `LOCAL`:
   - Hash the provided `password` (using `@shared/skills/security/hashing`).
   - Create a `LOCAL` [[models/Credential]].
5. If OAuth (`GOOGLE`/`APPLE`):
   - Create a [[models/Credential]] with the `externalId`.
6. Emit `UserRegistered` event.
7. Return `userId`.

## Emits
- `UserRegistered` (payload: `userId`, `email`)
