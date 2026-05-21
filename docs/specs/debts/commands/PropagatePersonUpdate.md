# Command: Propagate Person Update to Projections

## Goal
Update denormalized person details (like name) in all debt projections when a person is updated in the People module.

## Input
- `personId`: UUID
- `name`: String

## Preconditions
- (None; this is an internal command triggered by event subscribers.)

## Flow
1. Find all [[projections/DebtSummaryProjection]] where `personId` matches.
2. For each record:
   - Update `personName` to the provided `name`.
   - Set `updatedAt` to now.
3. Persist all changes.

## Postconditions
- All `DebtSummaryProjection` records for this `personId` reflect the updated name.

## Effects
- (None)

## Errors
- (None)

## Result
- `void`
