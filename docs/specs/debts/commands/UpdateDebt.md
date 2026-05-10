# Use Case: Update Debt

## Goal
Modify an existing debt's metadata.

## Flow
1. Receive `debtId`, and optional `name`, `dueDate`.
2. Find [[models/Debt]] with `debtId`.
3. If not found, return `DebtNotFound`.
4. Update properties if provided:
    - `name`
    - `dueDate`
5. Set `updatedAt` to now.
6. Store the updated [[models/Debt]].
7. Return the updated [[models/Debt]].

## Emits
- `DebtUpdated`

## Errors
- `DebtNotFound`
