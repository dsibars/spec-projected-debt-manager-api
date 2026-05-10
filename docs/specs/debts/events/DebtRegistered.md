# Event: Debt Registered

## Description
Emitted when a new debt is successfully created.

## Payload
- `eventId`: UUID (unique identifier for this event instance)
- `debtId`: UUID
- `tenantId`: UUID
- `personId`: UUID
- `totalAmount`: Integer
- `currency`: String
- `direction`: OWED_TO_ME | I_OWE
- `occurredAt`: DateTime

## Producers
- [[commands/CreateDebt]]

## Consumers
- [[commands/UpdateDebtSummary]]
- [[presentation/subscribers/SyncPayment]] (for future payment plan initialization)
