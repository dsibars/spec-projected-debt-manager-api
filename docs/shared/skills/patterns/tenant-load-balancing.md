# Skill: Tenant Load Balancing Strategy

## Category: patterns
## Provides:
- Tenant Load Balancing
## Conflicts With:
- clean-code
- cqrs-and-events
- data-replication
- module-boundaries
- outbox-inbox-schema
- resilience
- simplified-ddd
## Depends On:
- None explicitly declared


This skill defines the deterministic algorithm for assigning new users to tenants (shards).

## Principles
1.  **Capacity Awareness**: Never assign a user to a tenant that has reached its capacity.
2.  **Even Distribution**: Distribute users across available tenants.

## Atomic Load Increment Law
To prevent over-provisioning in a distributed system without needing global locks:
- The Shard Assignment MUST use an atomic database update on the Global Identity DB.
- **Query**: `UPDATE shards SET current_load = current_load + 1 WHERE id = :id AND current_load < capacity AND status = 'ACTIVE'`.
- If the rows affected is 0, the operation must retry with the next available shard or return `SystemOverloaded`.

## Selection Algorithm
1. Filter `ACTIVE` shards.
2. Sort by `currentLoad` (Ascending).
3. Attempt Atomic Increment on the top shard.
4. If successful, that shard is assigned.