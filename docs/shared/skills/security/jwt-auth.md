# Skill: JWT Authentication & Tenant Isolation

This skill defines the law for securing REST endpoints and extracting multi-tenancy context.

## Principles
1.  **Stateless Identity**: All authentication must be stateless using JSON Web Tokens (JWT).
2.  **Claim Extraction**:
    - The `sub` (Subject) claim of the JWT MUST be treated as the `userId`.
    - The `tid` (Tenant ID) claim of the JWT MUST be treated as the `tenantId`.
3.  **Mandatory Filter**: Every REST endpoint (except `/auth/*` and public health checks) MUST be protected by a middleware/interceptor that validates the token.
4.  **Application Injection**: The extracted `tenantId` (and `userId`) MUST be injected into the Command or Query as mandatory arguments.

## Technical Requirements
- **Algorithm**: RS256 (Public/Private Key) or HS256 (Shared Secret), as defined in the implementation `config`.
- **Validation**:
    - Expiration (`exp`) must be in the future.
    - Signature must be valid.
    - Issuer (`iss`) and Audience (`aud`) must match the `config`.
    - `tid` must be present and valid.
- **Error Handling**: If validation fails, return `401 Unauthorized` with a standardized JSON error.
