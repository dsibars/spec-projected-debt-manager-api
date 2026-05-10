# Event: Person Created

## Description
Emitted when a new person is registered.

## Payload
- `eventId`: UUID
- `personId`: UUID
- `tenantId`: UUID
- `name`: String
- `email`: String?
- `occurredAt`: DateTime

## Producers
- [[commands/CreatePerson]]

## Consumers
- [[specs/debts/presentation/subscribers/SyncPerson]]
