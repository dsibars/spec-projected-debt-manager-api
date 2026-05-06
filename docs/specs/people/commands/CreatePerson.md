# Use Case: Create Person

## Goal
To register a new person in the system with their basic identity and contact information.

## Flow
1. Receive `name`, `email` (optional), and `phone` (optional).
2. Validate that `name` is provided and not empty.
3. If `email` is provided, validate its format.
4. If `email` is provided, check if a non-archived person already exists with the same email in the Store.
5. Generate a unique `id`.
6. Set `isArchived` to `false`.
7. Set `createdAt` and `updatedAt` to the current system time.
8. Persist the new [[models/Person]] to the Store.
9. Return the newly created `id`.

## Emits
- `PersonCreated` (payload: `id`, `name`)

## Errors
- `InvalidName`: If the name is missing or empty.
- `InvalidEmailFormat`: If the provided email does not match standard patterns.
- `DuplicateEmail`: If another person already exists with the same email.
