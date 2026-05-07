# Command: Create Tenant

## Goal
Provision a new logical partition (Tenant) for the system.

## Input
- `name`: `string`
- `capacity`: `integer`
- `region`: `string`

## Flow
1. Verify that a Tenant with the same `name` does not already exist.
2. Generate a unique `tenantId`.
3. Create the [[models/Tenant]] record with status `ACTIVE`.
4. Emit `TenantCreated` event.
5. Return `tenantId`.

## Emits
- `TenantCreated` (payload: `tenantId`, `name`, `region`)
