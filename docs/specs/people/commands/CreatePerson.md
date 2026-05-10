# Use Case: Create Person

## Goal
To register a new person in the user's private directory.

## Input
- `tenantId`: `uuid` (The owning User, injected from Auth context)
- `name`: `string`
- `email`: `string?`
- `phone`: `string?`

## Flow
1. Validate that `name` is provided.
2. If `email` is provided:
   - Check if a non-archived person already exists with the same email **strictly within this `tenantId`**.
3. Generate a unique `id`.
4. Create the [[models/Person]] record with the provided `tenantId`.
5. Persist the record.
6. Emit `PersonCreated` event (carrying `tenantId` in envelope metadata).
7. Return `id`.

## Emits
- `PersonCreated`

## Errors
- `DuplicateEmail`: If another person already exists with the same email **for this user**.
