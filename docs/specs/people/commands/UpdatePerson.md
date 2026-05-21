# Command: Update Person

## Goal
Modify the details of an existing person.

## Input
- `tenantId`: `uuid` (Injected from Auth context)
- `id`: `uuid`
- `name`: `string?`
- `email`: `string?`
- `phone`: `string?`

## Preconditions
- The referenced person must exist and belong to the `tenantId`.
- If `email` is provided, it must not conflict with another non-archived person for this `tenantId`.

## Flow
1. Find the existing [[models/Person]] by `id` and `tenantId`.
2. If not found, raise `PersonNotFound`.
3. If `name` is provided, validate it is not empty and update the field.
4. If `email` is provided, validate its format and check for duplicates (excluding the current person).
5. If `phone` is provided, update the field.
6. Set `updatedAt` to the current system time.
7. Persist the changes to the Store.
8. Emit `PersonUpdated` event.

## Postconditions
- The `Person` aggregate reflects the updated fields.

## Effects
- Emits: `PersonUpdated`

## Errors
- `PersonNotFound`: If `id` does not exist.
- `InvalidName`: If the provided name is empty.
- `DuplicateEmail`: If the new email is already used by another non-archived person for this user.

## Result
- `id`: UUID of the updated person.
