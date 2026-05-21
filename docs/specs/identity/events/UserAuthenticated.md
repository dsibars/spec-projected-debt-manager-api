# Event: User Authenticated

## Description
Emitted when a user successfully authenticates and receives tokens.

## Payload
- `eventId`: UUID
- `userId`: UUID
- `occurredAt`: DateTime

## Producers
- [[commands/AuthenticateUser]]

## Consumers
- (None external; may be used for audit logging and security analytics)
