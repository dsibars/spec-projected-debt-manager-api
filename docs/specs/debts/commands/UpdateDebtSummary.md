# Command: Update Debt Summary Projection

## Goal
Keep the denormalized projection in sync with the source aggregates.

## Input
- `debtId`: `uuid`
- `personName`: `string?`
- `remainingAmount`: `integer?`
- `paymentDate`: `datetime?`
- `isSettled`: `boolean?`
- `isArchived`: `boolean?`

## Preconditions
- (None; this is an internal projection update command.)

## Flow
1. Find the [[projections/DebtSummaryProjection]] by `debtId`.
2. If not found, skip (or log warning if it should exist).
3. If `personName` is provided, update it.
4. If `remainingAmount` is provided:
   - Update `remainingAmount`.
   - Increment `paymentCount`.
   - Update `lastPaymentDate` with `paymentDate`.
5. If `isSettled` is provided, update it.
6. If `isArchived` is provided, update it.
7. Set `updatedAt` to now.
8. Persist to store.

## Postconditions
- The `DebtSummaryProjection` reflects the provided updates.

## Effects
- (None; projections do not emit domain events.)

## Errors
- (None; silent skip if projection not found.)

## Result
- `void`
