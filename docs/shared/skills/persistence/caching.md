# Skill: Application Caching (Redis)

## Category: persistence
## Provides:
- Application Caching
## Conflicts With:
- None
## Depends On:
- @shared/skills/persistence/postgresql

This skill defines how the application MUST use caching to achieve sub-100ms response times for read-heavy operations.

## Principles
1.  **Cache-Aside Pattern**: The application queries the cache first; on miss, queries the DB and populates the cache.
2.  **Invalidation on Write**: Any command that modifies an aggregate MUST invalidate the corresponding cache entries.
3.  **TTL-First**: Prefer automatic expiration over explicit invalidation where eventual consistency is acceptable.

## Cache Layers

### L1: Hot Data Cache (Redis)
- **User Sessions**: Store active session metadata (`userId`, `permissions`, `tenantId`). TTL: 24 hours.
- **User Profiles**: Cache `GetCurrentUser` results. TTL: 5 minutes.
- **Tenant Configs**: Small, rarely changing configuration per tenant. TTL: 1 hour.
- **Rate Limit Counters**: Sliding window counters per `tenantId` + endpoint. TTL: 1 minute.

### L2: Query Result Cache (Redis or In-Memory)
- **List Queries**: Cache paginated list results. TTL: 30 seconds.
- **Projections**: Cache hot `DebtSummaryProjection` queries. TTL: 1 minute.
- **Search Results**: Cache filtered search results. TTL: 2 minutes.

### L3: CDN / Edge Cache (Presentation Layer)
- **Static Assets**: API documentation UI, OpenAPI JSON specs.
- **Public Data**: Any non-sensitive, public-facing data.

## Cache Key Naming
`{module}:{tenantId}:{entity}:{entityId}:{queryHash?}`

Examples:
- `debts:550e8400:debt:uuid-123`
- `debts:550e8400:summary:list:page1:size20`
- `identity:550e8400:session:jti-abc`

## Invalidation Rules
- **On Command Success**: Delete cache keys for the affected entity.
- **On Bulk Operation**: Delete by pattern (e.g., `debts:550e8400:debt:*`).
- **On Tenant Purge**: Delete all keys matching `*:550e8400:*`.

## Technical Requirements
- **Redis**: Single primary instance or Redis Cluster for high availability.
- **Serialization**: Use language-native serialization (JSON, MessagePack) with compression for large objects.
- **Circuit Breaker**: If Redis is unavailable, degrade to direct DB queries (cache miss).

## Configuration Contract
- **Redis URL**: `cache.redis.url`
- **Default TTL**: `cache.default-ttl-seconds` (default: 300)
- **Max Object Size**: `cache.max-object-size-kb` (default: 1024)
