# Command: Propagate Person Update to Projections

## Goal
To update denormalized person details (like name) in all debt projections when a person is updated in the People module.

## Input
- `personId`: UUID
- `name`: String

## Flow
1. Find all [[projections/DebtSummaryProjection]] where `personId` matches.
2. For each record:
   - Update `personName` to the provided `name`.
   - Set `updatedAt` to now.
3. Persist all changes.
