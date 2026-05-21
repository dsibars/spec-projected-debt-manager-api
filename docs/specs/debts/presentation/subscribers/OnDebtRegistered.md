# Subscriber: On Debt Registered

## Goal
Create the denormalized DebtSummaryProjection when a new debt is registered.

## Subscribes To
- `debts.DebtRegistered`

## Flow
1. Receive **`EventEnvelope`**.
2. Extract `debtId`, `tenantId`, `personId`, `totalAmount`, `currency`, `direction` from payload.
3. Look up `personName` from local [[projections/PersonReadModel]] using `personId`.
4. Create a new [[projections/DebtSummaryProjection]] record:
   - `id`: `debtId`
   - `tenantId`: `tenantId`
   - `personId`: `personId`
   - `personName`: from PersonReadModel
   - `debtName`: from event payload (if available) or default
   - `totalAmount`: `totalAmount`
   - `remainingAmount`: `totalAmount`
   - `paymentCount`: 0
   - `lastPaymentDate`: null
   - `isSettled`: false
   - `isArchived`: false
   - `updatedAt`: now
5. Persist to the projection store.

## Result
- `void`
