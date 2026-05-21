# Use Case: Get Payment

## Goal
Retrieve details of a specific payment.

## Flow
1. Receive `paymentId`.
2. Query the Store for [[models/Payment]] with the given `paymentId`.
3. If not found, return `PaymentNotFound`.
4. Return the [[models/Payment]].

## Errors
- `PaymentNotFound`

## Result
- [[models/Payment]]
