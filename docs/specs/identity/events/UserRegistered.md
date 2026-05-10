# Event: User Registered

## Description
Emitted when a new user completes registration.

## Payload
- `eventId`: UUID (unique identifier for this event instance)
- `userId`: UUID
- `email`: String
- `occurredAt`: DateTime

## Producers
- [[commands/RegisterUser]]

## Consumers
- (None yet; reserved for future onboarding flows)
