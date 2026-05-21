# Event: Debt Updated

## Description
Emitted when a debt's metadata is modified.

## Payload
- `eventId`: UUID
- `debtId`: UUID
- `tenantId`: UUID
- `name`: String?
- `dueDate`: DateTime?
- `occurredAt`: DateTime

## Producers
- [[commands/UpdateDebt]]

## Consumers
- (None external; may be used for audit logging)
