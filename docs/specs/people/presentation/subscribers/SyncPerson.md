# Subscriber: Sync Person

## Goal
To automatically create a "Self" Person record whenever a new User is registered in the Identity module. This allows the user to be a participant in their own debts immediately.

## Listens To
- `UserRegistered` (from Identity module)

## Flow
1. Receive `UserRegistered` event.
2. Extract `userId` and `email`.
3. Check if a [[models/Person]] with `externalRef == userId` already exists for this tenant.
4. If not, create a new [[models/Person]]:
   - `id`: new `uuid`
   - `tenantId`: `userId` (from event)
   - `name`: `email` (initial name)
   - `externalRef`: `userId`
   - `isArchived`: `false`
5. Emit `PersonCreated` (internal to People module).
