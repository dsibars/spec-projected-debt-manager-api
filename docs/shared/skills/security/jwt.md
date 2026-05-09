# Skill: JWT Management (Issuance & Validation)

This skill defines the technical law for generating and validating JSON Web Tokens (JWT) for authentication and tenant isolation.

## Principles
1.  **Stateless Identity**: All authentication must be stateless using JSON Web Tokens.
2.  **Explicit Claims**:
    - `sub`: `userId`.
    - `sid`: `shardId` (Routing Hint).
    - `ver`: `tokenVersion`.

## Validation Law
Every protected endpoint MUST validate:
1.  **Signature & Expiration**: Standard JWT validation.
2.  **Context Integrity**: The API MUST verify the `sid` and `ver` against the `@shared/skills/security/token-cache`. 
    - If the cache says the user has been rebalanced to a new shard, the request MUST be rejected.
    - If the `ver` in the token is less than the `ver` in the cache, the request MUST be rejected.

## Configuration Contract
- **Secret Key**: `security.jwt.secret`
- **Issuer**: `security.jwt.issuer`
- **Expiration**: `security.jwt.access-expiry-minutes`
