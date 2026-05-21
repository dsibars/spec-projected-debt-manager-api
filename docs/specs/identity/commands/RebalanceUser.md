# Command: Rebalance User

## Goal
Move a user from one shard (physical partition) to another for load balancing or maintenance.

## Input
- `userId`: `uuid`
- `targetShardId`: `uuid`

## Preconditions
- The user must exist.
- The destination shard must be `ACTIVE` and have available capacity.

## Flow
1. Find the [[models/User]] by `userId`.
2. Find the current [[models/Shard]] (Source) and the `targetShardId` [[models/Shard]] (Destination).
3. Verify that Destination Shard is `ACTIVE` and has capacity.
4. Update [[models/User]] `shardId` to `targetShardId`.
5. Decrement `currentLoad` of Source Shard.
6. Increment `currentLoad` of Destination Shard.
7. Update the [[models/UserIndex]] to reflect the new `shardId`.
8. Emit `UserRebalanced` event.

## Postconditions
- The user's `shardId` points to the destination shard.
- Shard loads are updated accordingly.

## Effects
- Emits: `UserRebalanced` (payload: `userId`, `sourceShardId`, `destinationShardId`)

## Errors
- `UserNotFound`
- `ShardNotFound`
- `ShardFull`: If destination shard has no available capacity.
- `ShardUnderMaintenance`: If destination shard is not ACTIVE.

## Result
- `void`
