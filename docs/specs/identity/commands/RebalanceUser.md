# Command: Rebalance User

## Goal
Move a user from one shard (physical partition) to another for load balancing or maintenance.

## Input
- `userId`: `uuid`
- `targetShardId`: `uuid`

## Flow
1. Find the [[models/User]] by `userId`.
2. Find the current [[models/Shard]] (Source) and the `targetShardId` [[models/Shard]] (Destination).
3. Verify that Destination Shard is `ACTIVE` and has capacity.
4. Emit `UserRebalanceStarted` event.
5. Update [[models/User]] `shardId` to `targetShardId`.
6. Decrement `currentLoad` of Source Shard.
7. Increment `currentLoad` of Destination Shard.
8. Emit `UserRebalanced` event.

## Emits
- `UserRebalanceStarted` (payload: `userId`, `sourceShardId`, `destinationShardId`)
- `UserRebalanced` (payload: `userId`, `sourceShardId`, `destinationShardId`)

## Note
This is a high-level administrative command. The `UserRebalanced` event triggers data migration subscribers in all other modules to move the isolated user data between physical databases if necessary.
