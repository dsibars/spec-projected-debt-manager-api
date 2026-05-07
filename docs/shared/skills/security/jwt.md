# Skill: JWT Management (Issuance & Validation)

This skill defines the technical law for generating and validating JSON Web Tokens (JWT) for authentication and tenant isolation.

## Principles
1.  **Stateless Identity**: All authentication must be stateless using JSON Web Tokens.
2.  **Explicit Claims**:
    - The `sub` (Subject) claim MUST represent the `userId`.
    - The `tid` (Tenant ID) claim MUST represent the `tenantId`.
3.  **Mandatory Validation**: Every protected endpoint MUST validate the token before processing.

## Technical Requirements
- **Algorithm**: RS256 (Public/Private Key) or HS256 (Shared Secret), as defined in the implementation `config`.
- **Validation Rules**:
    - Expiration (`exp`) must be in the future.
    - Signature must be valid.
    - Issuer (`iss`) and Audience (`aud`) must match the `config`.
    - `tid` and `sub` MUST be present.
- **Issuance Rules**:
    - `iat` (Issued At) must be the current time.
    - `exp` should be configurable (e.g., 15 minutes for Access Token, 7 days for Refresh Token).

## Implementation Interface
The Builder must synthesize a `TokenPort`:
- `generateAccessToken(user: User): String`
- `generateRefreshToken(user: User): String`
- `validateToken(token: String): TokenClaims`

## Error Handling
If validation fails, the Presentation layer must return `401 Unauthorized` with a standardized JSON error.
