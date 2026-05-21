# Command: Authenticate User

## Goal
Validate credentials and issue tokens containing the user identity.

## Input
- `email`: `string`
- `password`: `string?`
- `provider`: `LOCAL | GOOGLE | APPLE`
- `externalId`: `string?`

## Preconditions
- The user must exist and be active.
- Credentials must match the provided provider.

## Flow
1. Find the [[models/User]] by `email`.
2. If not found or `isActive` is `false`, raise `AuthenticationFailed`.
3. Find the [[models/Credential]] for this `userId` and `provider`.
4. If `LOCAL`:
   - Verify `password` against the hashed secret (using `@shared/skills/security/hashing`).
5. If OAuth:
   - Verify `externalId` matches the secret.
6. Update `lastLoginAt` on [[models/User]].
7. Generate Access and Refresh Tokens (using `@shared/skills/security/jwt`).
   - The token MUST include `userId` (`sub` claim).

## Postconditions
- The user's `lastLoginAt` is updated.
- Valid tokens are returned.

## Effects
- Emits: `UserAuthenticated`

## Errors
- `AuthenticationFailed`: User not found or inactive.
- `TokenGenerationFailed`: If token issuance fails.
- `InvalidCredentials`: Password or externalId verification failed.

## Result
- `accessToken`: `string`
- `refreshToken`: `string`
