# Skill: Security - Token Cache (High Performance)

## Category: security
## Provides:
- Token Cache
## Conflicts With:
- hashing
- jwt
- oauth-integration
- pii-management
- traffic-governance
## Depends On:
- None explicitly declared


This skill defines the law for caching security metadata to avoid expensive database lookups during stateless authentication.

## 1. The Requirement
To validate JWT `tokenVersion` and `shardId` without querying the Shard DB on every request, the system MUST utilize a high-performance, in-memory cache.

## 2. Technical Standards
- **Provider**: Redis (or similar distributed KV store).
- **Key Pattern**: `security:user:[userId]:context`
- **Stored Values**:
    - `shardId`: UUID
    - `tokenVersion`: Integer
    - `status`: User status (e.g., ACTIVE, BLOCKED)
- **TTL (Time-to-Live)**: 
    - The TTL SHOULD match the `security.jwt.access-expiry-minutes` or slightly exceed it.
    - If a cache miss occurs, the system MUST fallback to the Shard DB and populate the cache.

## 3. Invalidation Policy (The Cache Law)
- **State Changes**: Any change to a User's `shardId`, `tokenVersion`, or `isActive` status MUST immediately invalidate or update the corresponding Redis key.
- **Consistency**: Use a "Write-Through" or "Cache-Aside" pattern that favors consistency for security-critical attributes.

## 4. Configuration Contract
- **Redis URL**: `cache.security.url`
- **Cache Name**: `security-context`