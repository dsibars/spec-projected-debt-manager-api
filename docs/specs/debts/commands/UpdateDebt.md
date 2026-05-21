# Command: Update Debt

## Goal
Modify an existing debt's metadata.

## Input
- `tenantId`: UUID (Injected from Auth context)
- `debtId`: UUID
- `name`: String?
- `dueDate`: DateTime?

## Preconditions
- The referenced debt must exist and belong to the `tenantId`.
- The referenced debt must not be archived.

## Flow
1. Find [[models/Debt]] with `debtId` and `tenantId`.
2. If not found, raise `DebtNotFound`.
3. Update properties if provided:
   - `name`
   - `dueDate`
4. Set `updatedAt` to now.
5. Persist the updated [[models/Debt]].
6. Update [[projections/DebtSummaryProjection]] via [[commands/UpdateDebtSummary]] with:
   - `debtId`: The debt ID.
   - `debtName`: The updated name, if changed.

## Postconditions
- The `Debt` aggregate reflects the updated fields.
- The `DebtSummaryProjection` reflects any name change.

## Effects
- Emits: `DebtUpdated`

## Errors
- `DebtNotFound`

## Result
- `id`: UUID of the updated debt.
