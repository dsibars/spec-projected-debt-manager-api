# Event: Debt Settled

## Description
Emitted when a debt's currentBalance reaches zero.

## Payload
- `eventId`: UUID
- `debtId`: UUID
- `tenantId`: UUID
- `occurredAt`: DateTime

## Producers
- [[commands/ApplyPaymentToDebt]]

## Consumers
- (None yet; reserved for future analytics/notifications)
