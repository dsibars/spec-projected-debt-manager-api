# Skill: External OAuth Integration (Google & Apple)

This skill defines how the system interacts with external Identity Providers (IdP).

## Principles
1.  **Trust but Verify**: The system must never trust an `externalId` provided directly by a client without verifying it against the IdP.
2.  **Token Exchange**: Clients provide an `idToken` (OIDC) from the IdP. The system validates this token and extracts the `subject` (externalId) and `email`.
3.  **Cross-Check**: If a user logs in via OAuth, the `email` from the IdP MUST match the `email` in our system for that User.

## Technical Requirements
- **Verification**: Use the IdP's public keys (retrieved via JWKS) to verify the `idToken` signature.
- **Claims Validation**:
  - `aud` (Audience) MUST match our Client ID.
  - `iss` (Issuer) MUST match the IdP's expected URL (`https://accounts.google.com` or `https://appleid.apple.com`).
  - `exp` (Expiration) MUST be in the future.
- **Mapping**:
  - Google `sub` -> `externalId`
  - Apple `sub` -> `externalId`

## Implementation Interface
The Builder must synthesize an `OAuthPort` for each provider:
- `verifyToken(idToken: String): ExternalIdentity`
- `ExternalIdentity` contains `externalId`, `email`, and `providerData`.
