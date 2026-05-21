# Skill: JWT Management (Issuance & Validation)

## Category: security
## Provides:
- Jwt Management
## Conflicts With:
- None
## Depends On:
- @shared/skills/security/hashing

This skill defines the technical law for generating and validating JSON Web Tokens (JWT) for authentication.

## Principles
1.  **Stateless Identity**: All authentication must be stateless using JSON Web Tokens.
2.  **Explicit Claims**:
    - `sub`: `userId` (also functions as `tenantId` in this personal ledger architecture).
    - `iss`: Issuer identifier.
    - `iat`: Issued at timestamp.
    - `exp`: Expiration timestamp.
    - `jti`: Unique token identifier (for revocation support).

## Validation Law
Every protected endpoint MUST validate:
1.  **Signature & Expiration**: Standard JWT validation using the configured secret.
2.  **Issuer**: The `iss` claim must match the expected issuer.
3.  **Revocation**: For high-security scenarios, verify the `jti` against a revocation list (e.g., Redis) to support logout and forced re-authentication.

## Session Revocation (Optional but Recommended)
To support logout and security incidents:
- Maintain a revocation set (e.g., Redis Set) of invalidated `jti` values.
- On logout or security event, add the token's `jti` to the revocation set with TTL matching the token's remaining lifetime.
- On validation, check the `jti` against the revocation set.

## Configuration Contract
- **Secret Key**: `security.jwt.secret`
- **Issuer**: `security.jwt.issuer`
- **Access Expiration**: `security.jwt.access-expiry-minutes` (default: 15)
- **Refresh Expiration**: `security.jwt.refresh-expiry-days` (default: 7)
