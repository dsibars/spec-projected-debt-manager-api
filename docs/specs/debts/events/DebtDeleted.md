# Event: Debt Deleted (Archived)

## Description
Emitted when a debt is soft-deleted (archived).

## Payload
- `eventId`: UUID
- `debtId`: UUID
- `tenantId`: UUID
- `occurredAt`: DateTime

## Producers
- [[commands/DeleteDebt]]

## Consumers
- (None external; may be used for audit logging)
