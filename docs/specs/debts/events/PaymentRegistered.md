# Event: Payment Registered

## Description
Emitted when a payment is registered against a debt.

## Payload
- `eventId`: UUID
- `paymentId`: UUID
- `debtId`: UUID
- `tenantId`: UUID
- `amount`: Integer
- `occurredAt`: DateTime

## Producers
- [[commands/RegisterPayment]]

## Consumers
- None yet
