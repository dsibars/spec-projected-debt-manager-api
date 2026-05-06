# Use Case: Create Debt

## Goal
Initialize a new debt record.

## Flow
1. Receive `personId`, `name`, `totalAmount`, `direction`, `currency` (optional), and `dueDate` (optional).
2. Validate that `personId` exists in the local [[models/PersonReadModel]] and `isArchived` is `false`.
3. Validate that `totalAmount` is positive.
4. Generate a unique `id`.
5. Set `currentBalance` equal to `totalAmount`.
6. Set `isSettled` to `false`.
7. Set `createdAt` and `updatedAt` to now.
8. Store the new [[models/Debt]].
9. Return the created `id`.

## Emits
- `DebtRegistered` (payload: `id`, `personId`, `totalAmount`, `currentBalance`)

## Errors
- `PersonNotFound`: If `personId` is invalid or archived in the local read model.
- `InvalidAmount`: If `totalAmount` <= 0.
