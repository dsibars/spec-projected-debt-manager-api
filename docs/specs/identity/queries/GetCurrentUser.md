# Query: GetCurrentUser

## Intent
Retrieve the profile of the currently authenticated user.

## Inputs
- `context.userId`: `UUID` (Mandatory)

## Outputs
- `id`: `UUID`
- `email`: `String`
- `tenantId`: `UUID`

## Logic
1. Fetch the User from the database using the `userId` from context.
2. If not found, return `UserNotFound` error.
3. Return the user data.
