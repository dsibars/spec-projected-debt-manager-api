# Model: Debt Summary Projection

## Goal
A denormalized, ready-to-render view of a debt and its related data (Person, Payments).

## Properties
- `id`: `uuid` (Primary Key, matches Debt ID)
- `tenantId`: `uuid` (Owner of this record)
- `personId`: `uuid`
- `personName`: `string` (Denormalized from People module)
- `debtName`: `string`
- `totalAmount`: `integer`
- `remainingAmount`: `integer` (Computed)
- `paymentCount`: `integer` (Computed)
- `lastPaymentDate`: `datetime?` (Computed)
- `isSettled`: `boolean`
- `isArchived`: `boolean` (Default: `false`)
- `updatedAt`: `datetime`
