# Skill: Security - Traffic Governance (Rate Limiting)

## Category: security
## Provides:
- Traffic Governance
## Conflicts With:
- hashing
- jwt
- oauth-integration
- pii-management
- token-cache
## Depends On:
- None explicitly declared


This skill defines how the system protects itself from resource exhaustion and "Noisy Neighbor" effects.

## 1. Tenant-Level Rate Limiting
The system MUST implement a **Leaky Bucket** or **Token Bucket** algorithm for rate limiting.
- **Scope**: Limits are applied per `tenantId` (extracted from the JWT).
- **Default Limit**: `100` requests per minute per tenant.

## 2. Resource Protection
If a tenant exceeds their limit:
- The system MUST return `429 Too Many Requests`.
- The response MUST include a `Retry-After` header.

## 3. Configuration Contract
- **Default Limit**: `security.rate-limit.default-rpm` (Default: `100`).
- **Burst Capacity**: `security.rate-limit.burst` (Default: `20`).

## 4. Telemetry
- Every `429` event MUST be emitted as a metric to SigNoz to identify "Aggressive Tenants."