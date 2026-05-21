# Command: Delete Person (Archive)

## Goal
Mark a person as archived so they no longer appear in active lists, while preserving their historical data.

## Input
- `tenantId`: `uuid` (Injected from Auth context)
- `id`: `uuid`

## Preconditions
- The referenced person must exist and belong to the `tenantId`.

## Flow
1. Find the existing [[models/Person]] by `id` and `tenantId`.
2. If not found, raise `PersonNotFound`.
3. Set `isArchived` to `true`.
4. Set `updatedAt` to the current system time.
5. Persist the changes to the Store.
6. Emit `PersonArchived` event.

## Postconditions
- The person's `isArchived` flag is `true`.
- The person does not appear in default list queries.

## Effects
- Emits: `PersonArchived` (payload: `id`)

## Errors
- `PersonNotFound`: If the `id` does not match any existing person for this tenant.

## Result
- `void`
