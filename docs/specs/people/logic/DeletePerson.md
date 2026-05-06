# Use Case: Delete Person (Archive)

## Goal
Mark a person as archived so they no longer appear in active lists, while preserving their historical data.

## Flow
1. Receive `id`.
2. Find the existing [[models/Person]] in the Store.
3. If not found, return an error.
4. Set `isArchived` to `true`.
5. Set `updatedAt` to the current system time.
6. Persist the changes to the Store.
7. Return the `id`.

## Emits
- `PersonArchived` (payload: `id`)

## Errors
- `PersonNotFound`: If the `id` does not match any existing person.
