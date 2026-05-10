# Command: Authenticate User

## Emits
- `UserAuthenticated`

## Errors
- `InvalidCredentials`

## Goal
Validate credentials and issue tokens containing both user and shard context.

## Input
- `email`: `string`
- `password`: `string?`
- `provider`: `LOCAL | GOOGLE | APPLE`
- `externalId`: `string?`

## Flow
1. Find the [[models/User]] by `email`.
2. If not found or `isActive` is `false`, return `AuthenticationFailed`.
3. Find the [[models/Credential]] for this `userId` and `provider`.
4. If `LOCAL`:
   - Verify `password` against the hashed secret (using `@shared/skills/security/hashing`).
5. If OAuth:
   - Verify `externalId` matches the secret.
6. Update `lastLoginAt` on [[models/User]].
7. Generate Access and Refresh Tokens (using `@shared/skills/security/jwt`).
   - The token MUST include `userId` (`sub` claim).
   - The token MUST include `shardId` (`sid` claim) from the [[models/User]] record.
8. Return tokens.

## Result
- `AccessToken`: `string`
- `RefreshToken`: `string`
