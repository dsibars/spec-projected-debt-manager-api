# Event: Person Archived

## Description
Emitted when a person is soft-deleted (archived).

## Payload
- `eventId`: UUID
- `id`: UUID
- `tenantId`: UUID
- `occurredAt`: DateTime

## Producers
- [[commands/DeletePerson]]

## Consumers
- [[specs/debts/presentation/subscribers/SyncPerson]] (updates local PersonReadModel)
