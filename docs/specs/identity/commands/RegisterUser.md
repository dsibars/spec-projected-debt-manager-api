# Command: Register User

## Goal
Create a new identity with email uniqueness enforcement.

## Input
- `email`: `string`
- `password`: `string?`
- `provider`: `LOCAL | GOOGLE | APPLE`
- `externalId`: `string?`

## Preconditions
- The `email` must not already be registered in the global `UserIndex`.

## Flow
1. **Global Uniqueness Check**:
   - Query the [[models/UserIndex]] in the Global DB to ensure `email` is not already registered.
   - If found, raise `EmailAlreadyInUse`.
3. Generate a unique `userId`.
4. **Local Creation**:
   - Create the [[models/User]] record.
5. **Credential Creation**:
   - Create a [[models/Credential]] record linked to the `userId`.
6. **Index Finalization**:
   - Create the [[models/UserIndex]] record in the Global DB to lock the email to the generated `userId`.
7. Emit `UserRegistered` event.

## Postconditions
- A `User` aggregate exists.
- A `Credential` exists for the user.
- The global `UserIndex` contains the email mapping.

## Effects
- Emits: `UserRegistered` (payload: `userId`, `email`)

## Errors
- `EmailAlreadyInUse`: If `email` is already registered.
- `EmailFormatInvalid`: If `email` is not a valid format.

## Result
- `userId`: UUID of the created user.
