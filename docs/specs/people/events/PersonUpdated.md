# Event: Person Updated

## Description
Emitted when a person's details are modified.

## Payload
- `eventId`: UUID
- `personId`: UUID
- `tenantId`: UUID
- `name`: String
- `email`: String?
- `phone`: String?
- `occurredAt`: DateTime

## Producers
- [[commands/UpdatePerson]]

## Consumers
- [[specs/debts/presentation/subscribers/SyncPerson]]
