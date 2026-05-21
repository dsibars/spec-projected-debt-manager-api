# Command: Delete Debt (Archive)

## Goal
Mark a debt as archived to preserve historical data while removing it from active lists.

## Input
- `tenantId`: UUID (Injected from Auth context)
- `debtId`: UUID

## Preconditions
- The referenced debt must exist and belong to the `tenantId`.

## Flow
1. Find [[models/Debt]] with `debtId` and `tenantId`.
2. If not found, raise `DebtNotFound`.
3. Set `isArchived` to `true`.
4. Set `updatedAt` to now.
5. Persist the updated [[models/Debt]].
6. Update [[projections/DebtSummaryProjection]] via [[commands/UpdateDebtSummary]] with `isArchived: true`.

## Postconditions
- The debt's `isArchived` flag is `true`.
- The debt does not appear in default list queries.

## Effects
- Emits: `DebtDeleted`

## Errors
- `DebtNotFound`

## Result
- `void`
