# Skill: Tenant Load Balancing Strategy

This skill defines the deterministic algorithm for assigning new users to tenants (shards).

## Principles
1.  **Capacity Awareness**: Never assign a user to a tenant that has reached its capacity.
2.  **Even Distribution**: Distribute users across available tenants to prevent hotspots.
3.  **Stability**: Once a user is assigned, their `tenantId` is immutable except via an explicit `RebalanceUser` command.

## Assignment Algorithm (Deterministic)
When the `RegisterUser` command requests a `tenantId`:

1.  **Filter**: Identify all Tenants with `status == 'ACTIVE'`.
2.  **Capacity Check**: Exclude any Tenant where `currentLoad >= capacity`.
3.  **Sort**: Sort the remaining Tenants by the following priority:
    - Primary: `currentLoad` (Ascending) - *Pick the least loaded shard first.*
    - Secondary: `id` (Ascending) - *Tie-breaker for perfectly equal loads.*
4.  **Selection**: Pick the first Tenant from the sorted list.
5.  **Failure**: If the list is empty, the system MUST return a `SystemOverloaded` Domain Error.

## Implementation Rules
- The Builder must ensure this logic is thread-safe or handled within a database transaction to prevent over-subscription of a tenant during concurrent registrations.
