# Use Case: Update Person

## Goal
Modify the details of an existing person.

## Flow
1. Receive `id`, and optional `name`, `email`, `phone`.
2. Find the existing [[models/Person]] in the Store.
3. If not found, return an error.
4. If `name` is provided, validate it is not empty and update the field.
5. If `email` is provided, validate its format and check for duplicates (excluding the current person).
6. If `phone` is provided, update the field.
7. Set `updatedAt` to the current system time.
8. Persist the changes to the Store.
9. Return the updated [[models/Person]].

## Emits
- `PersonUpdated`

## Errors
- `PersonNotFound`: If `id` does not exist.
- `InvalidName`: If the provided name is empty.
- `DuplicateEmail`: If the new email is already used by another non-archived person **for this user**.
