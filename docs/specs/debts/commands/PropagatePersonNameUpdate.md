# Command: Propagate Person Name to Projections

## Goal
To update the denormalized name in all debt projections when a person's name changes.

## Input
- `personId`: `uuid`
- `newName`: `string`

## Flow
1. Find all [[projections/DebtSummaryProjection]] where `personId` matches.
2. For each record:
   - Update `personName` to `newName`.
   - Set `updatedAt` to now.
3. Persist all changes.
