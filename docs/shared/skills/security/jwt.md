# Skill: JWT Management (Issuance & Validation)

This skill defines the technical law for generating and validating JSON Web Tokens (JWT) for authentication and tenant isolation.

## Principles
1.  **Stateless Identity**: All authentication must be stateless using JSON Web Tokens.
2.  **Explicit Claims**:
    - The `sub` (Subject) claim MUST represent the `userId` (The Logical Tenant).
    - The `sid` (Shard ID) claim MUST represent the `shardId` (The Physical Partition).
3.  **Mandatory Validation**: Every protected endpoint MUST validate the token before processing.

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Secret Key**: `security.jwt.secret` (MANDATORY in Prod)
- **Issuer**: `security.jwt.issuer` (Default: `spd-debt-manager`)
- **Expiration**: `security.jwt.access-expiry-minutes` (Default: `15`)

## Technical Requirements
- **Algorithm**: HS256 (Shared Secret) for this MVP, upgradable to RS256.
- **Validation Rules**:
    - Expiration (`exp`) must be in the future.
    - Signature must be valid.
    - Issuer (`iss`) must match the config.
    - `sid` and `sub` MUST be present.

## Implementation Interface
The Builder must synthesize a `TokenPort`:
- `generateAccessToken(user: User): String`
- `generateRefreshToken(user: User): String`
- `validateToken(token: String): TokenClaims`

## Error Handling
If validation fails, the Presentation layer must return `401 Unauthorized`.
