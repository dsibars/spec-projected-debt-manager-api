# Query: Get Current User

## Goal
Retrieve the profile of the currently authenticated user.

## Input
- `tenantId`: UUID (Injected from Auth context, equivalent to `userId`)

## Flow
1. Fetch the [[models/User]] from the database using the `tenantId`.
2. If not found, raise `UserNotFound`.
3. Return the user profile.

## Errors
- `UserNotFound`

## Result
- [[models/User]] profile (sanitized, no credentials)
