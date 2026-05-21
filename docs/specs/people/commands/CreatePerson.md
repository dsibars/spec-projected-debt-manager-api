# Command: Create Person

## Goal
Register a new person in the user's private directory.

## Input
- `tenantId`: `uuid` (The owning User, injected from Auth context)
- `name`: `string`
- `email`: `string?`
- `phone`: `string?`

## Preconditions
- `name` must be non-empty.
- If `email` is provided, it must be unique per `tenantId` among non-archived persons.

## Flow
1. Validate that `name` is non-empty.
2. If `email` is provided:
   - Check if a non-archived person already exists with the same email strictly within this `tenantId`.
   - If found, raise `DuplicateEmail`.
3. Generate a unique `id`.
4. Create the [[models/Person]] record with the provided `tenantId`.
5. Persist the record.
6. Emit `PersonCreated` event (carrying `tenantId` in envelope metadata).

## Postconditions
- A `Person` aggregate exists for this tenant.

## Effects
- Emits: `PersonCreated`

## Errors
- `InvalidName`: If `name` is empty or whitespace-only.
- `DuplicateEmail`: If another person already exists with the same email for this user.

## Result
- `id`: UUID of the created person.
