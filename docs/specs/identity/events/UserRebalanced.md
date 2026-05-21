# Event: User Rebalanced

## Description
Emitted when a user has been successfully moved to a new shard.

## Payload
- `eventId`: UUID
- `userId`: UUID
- `sourceShardId`: UUID
- `destinationShardId`: UUID
- `occurredAt`: DateTime

## Producers
- [[commands/RebalanceUser]]

## Consumers
- (None external; may be used for audit and cache invalidation)
