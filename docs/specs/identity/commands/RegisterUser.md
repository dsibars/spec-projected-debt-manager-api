# Command: Register User

## Goal
Create a new identity and assign it to an available tenant.

## Input
- `email`: `string`
- `password`: `string?`
- `provider`: `LOCAL | GOOGLE | APPLE`
- `externalId`: `string?` (For OAuth)

## Flow
1. Validate that `email` is not already in use.
2. **Tenant Assignment**:
   - Find an `ACTIVE` [[models/Tenant]] with the lowest `currentLoad` that is below its `capacity`.
   - If no tenant is available, return `SystemOverloaded` error.
   - Selected Tenant ID becomes the `tenantId` for this user.
3. Generate a unique `userId`.
4. Create the [[models/User]] record with the assigned `tenantId`.
5. Increment `currentLoad` on the selected [[models/Tenant]].
6. If `LOCAL`:
   - Hash the provided `password` (using `@shared/skills/security/hashing`).
   - Create a `LOCAL` [[models/Credential]].
7. If OAuth (`GOOGLE`/`APPLE`):
   - Create a [[models/Credential]] with the `externalId`.
8. Emit `UserRegistered` event.
9. Return `userId`.

## Emits
- `UserRegistered` (payload: `userId`, `tenantId`, `email`)
