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

## Postconditions
- The `Debt` aggregate reflects the updated fields.
- The read model will be updated via eventual consistency.

## Effects
- Emits: `DebtUpdated`

## Errors
- `DebtNotFound`

## Result
- `id`: UUID of the updated debt.
