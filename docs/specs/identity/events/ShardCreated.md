# Event: Shard Created

## Description
Emitted when a new shard is provisioned.

## Payload
- `eventId`: UUID
- `shardId`: UUID
- `name`: String
- `region`: String
- `capacity`: Integer
- `occurredAt`: DateTime

## Producers
- [[commands/CreateShard]]

## Consumers
- (None external; may be used for monitoring and capacity planning)
