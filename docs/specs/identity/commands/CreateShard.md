# Command: Create Shard

## Goal
Provision a new physical partition (Shard) for the system.

## Input
- `name`: `string`
- `capacity`: `integer`
- `region`: `string`

## Flow
1. Verify that a Shard with the same `name` does not already exist.
2. Generate a unique `shardId`.
3. Create the [[models/Shard]] record with status `ACTIVE` and `currentLoad: 0`.
4. Emit `ShardCreated` event.
5. Return `shardId`.

## Emits
- `ShardCreated` (payload: `shardId`, `name`, `region`)
