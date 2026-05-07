# Command: Rebalance User

## Goal
Move a user from one tenant (partition) to another for load balancing or maintenance.

## Input
- `userId`: `uuid`
- `targetTenantId`: `uuid`

## Flow
1. Find the [[models/User]] by `userId`.
2. Find the current [[models/Tenant]] (Source) and the `targetTenantId` [[models/Tenant]] (Destination).
3. Verify that Destination Tenant is `ACTIVE` and has capacity.
4. Emit `UserRebalanceStarted` event.
5. Update [[models/User]] `tenantId` to `targetTenantId`.
6. Decrement `currentLoad` of Source Tenant.
7. Increment `currentLoad` of Destination Tenant.
8. Emit `UserRebalanced` event.

## Emits
- `UserRebalanceStarted` (payload: `userId`, `sourceTenantId`, `destinationTenantId`)
- `UserRebalanced` (payload: `userId`, `sourceTenantId`, `destinationTenantId`)

## Note
This is a high-level declarative command. In a real-world distributed system, this would trigger a data migration process across all modules that use this `userId`.
