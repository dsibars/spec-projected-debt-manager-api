# Use Case: Delete Debt (Archive)

## Goal
Mark a debt as archived to preserve historical data while removing it from active lists.

## Flow
1. Receive `debtId`.
2. Find [[models/Debt]] with `debtId`.
3. If not found, return `DebtNotFound`.
4. Set `isArchived` to `true`.
5. Set `updatedAt` to now.
6. Store the updated [[models/Debt]].
7. Update [[projections/DebtSummaryProjection]] via [[UpdateDebtSummary]] with:
   - `debtId`: The `debtId`.
   - `isArchived`: `true`.
8. Return nothing (success).

## Errors
- `DebtNotFound`
