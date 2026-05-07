# Model: Shard Aggregate

## Goal
To represent a physical partition of data within the system for extreme horizontal scaling.

## Properties
- `id`: `uuid` (Primary Key, the `shardId`)
- `name`: `string` (Human-readable identifier, e.g., "Shard-US-01")
- `status`: `ACTIVE | FULL | MAINTENANCE | DORMANT`
- `capacity`: `integer` (Maximum number of users this shard can handle)
- `currentLoad`: `integer` (Current count of users assigned to this shard)
- `region`: `string` (Geographic location for data residency)
- `createdAt`: `datetime`
- `updatedAt`: `datetime`

---

# Business Rules
- A Shard in `FULL` status cannot accept new User registrations.
- A Shard in `MAINTENANCE` status restricts write operations for all users it contains.
- `currentLoad` must be tracked whenever a User is assigned or removed.
