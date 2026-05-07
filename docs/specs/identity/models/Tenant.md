# Model: Tenant Aggregate

## Goal
To represent a logical partition of data within the system for scaling and isolation.

## Properties
- `id`: `uuid` (Primary Key)
- `name`: `string` (Human-readable identifier for the partition, e.g., "Shard-US-01")
- `status`: `ACTIVE | FULL | MAINTENANCE | DORMANT`
- `capacity`: `integer` (Maximum number of users this tenant/shard can comfortably handle)
- `currentLoad`: `integer` (Current count of users assigned to this tenant)
- `region`: `string` (Geographic location for data residency requirements)
- `createdAt`: `datetime`
- `updatedAt`: `datetime`

---

# Business Rules
- A Tenant in `FULL` status cannot accept new User registrations.
- A Tenant in `MAINTENANCE` status may restrict write operations across all modules for the users it contains.
- `currentLoad` must be incremented whenever a User is assigned and decremented if a User is rebalanced out or deleted.
