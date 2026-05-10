# Event: Payment Applied

## Description
Emitted when a payment is applied to a debt in the Payments module.

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
- [[specs/debts/commands/ApplyPaymentToDebt]]
