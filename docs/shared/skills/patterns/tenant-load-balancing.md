# Skill: Tenant Isolation & Scaling Strategy

## Category: patterns
## Provides:
- Tenant Isolation
## Conflicts With:
- None
## Depends On:
- @shared/skills/persistence/postgresql

This skill defines how multi-tenant isolation and horizontal scaling are achieved at the infrastructure level, without polluting the domain model.

## Principles
1.  **Domain Purity**: The domain model MUST NOT contain infrastructure scaling concepts (shards, partitions, cells). Tenant isolation is achieved via the `tenantId` column on every aggregate.
2.  **Infrastructure Scaling**: Horizontal scaling is handled by:
    - **PostgreSQL Read Replicas**: For scaling read-heavy query workloads.
    - **Connection Pooling**: Per-tenant or per-application connection pools.
    - **Application Caching**: Redis for hot data (user profiles, session data).
    - **Database Partitioning**: If needed, use PostgreSQL native table partitioning by `tenantId` (declarative partitioning) or external tools like Citus.

## Tenant Isolation Law
- Every table MUST include a `tenant_id` column.
- Every query MUST filter by `tenant_id`.
- No cross-tenant queries are permitted at the application level.
- Foreign keys MUST never span tenants.

## Query Routing
- The Presentation layer extracts `tenantId` from the JWT `sub` claim.
- The application routes queries to read replicas transparently (via connection pool configuration).
- There is NO application-level shard selection logic.
