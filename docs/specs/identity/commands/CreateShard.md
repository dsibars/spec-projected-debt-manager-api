# Command: Create Shard

## Goal
Provision a new physical partition (Shard) for the system.

## Input
- `name`: `string`
- `capacity`: `integer`
- `region`: `string`

## Preconditions
- A Shard with the same `name` must not already exist.
- `capacity` must be greater than 0.

## Flow
1. Verify that a Shard with the same `name` does not already exist.
2. Generate a unique `shardId`.
3. Create the [[models/Shard]] record with status `ACTIVE` and `currentLoad: 0`.
4. Emit `ShardCreated` event.

## Postconditions
- A `Shard` aggregate exists with `currentLoad == 0`.

## Effects
- Emits: `ShardCreated` (payload: `shardId`, `name`, `region`)

## Errors
- `ShardNameAlreadyExists`: If a shard with this name already exists.
- `InvalidCapacity`: If `capacity <= 0`.

## Result
- `shardId`: UUID of the created shard.
