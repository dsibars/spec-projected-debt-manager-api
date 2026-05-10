# Subscriber: Sync Payment

## Goal
To adapt incoming Payment events to the debt aggregate update command.

## Subscribes To
- `payments.PaymentApplied`

## Adapts
- [[../../commands/ApplyPaymentToDebt]]

## Flow
1. Receive **`EventEnvelope`**. Extract `debtId` and `amount` from `data`.
2. Call [[../../commands/ApplyPaymentToDebt]] with `debtId` and `amount`.
