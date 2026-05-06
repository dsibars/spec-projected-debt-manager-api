# Command: Authenticate User

## Goal
Validate credentials and issue tokens.

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
   - Verify `password` against the hashed secret.
5. If OAuth:
   - Verify `externalId` matches the secret.
6. Update `lastLoginAt` on [[models/User]].
7. Generate Access and Refresh Tokens (using `@shared/skills/security/jwt`).
8. Return tokens.

## Result
- `AccessToken`: `string`
- `RefreshToken`: `string`
