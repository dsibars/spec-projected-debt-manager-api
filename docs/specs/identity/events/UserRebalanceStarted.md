# Event: User Rebalance Started

## Description
Emitted when a user shard rebalance operation begins.

## Payload
- `eventId`: UUID
- `userId`: UUID
- `sourceShardId`: UUID
- `destinationShardId`: UUID
- `occurredAt`: DateTime

## Producers
- [[commands/RebalanceUser]]

## Consumers
- (None external; may be used for audit and monitoring)
