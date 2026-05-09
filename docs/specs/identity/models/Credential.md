# Model: Credential

## Goal
To store the secrets required for authentication.

## Properties
- `id`: `uuid` (Primary Key)
- `userId`: `uuid` (Foreign Key to [[User]])
- `provider`: `LOCAL | GOOGLE | APPLE`
- `secret`: `string` (Hashed password or external subject ID)
- `providerData`: `json?` (Extra data like Google profile info)
- `createdAt`: `datetime`

## Constraints
- A User can have multiple Credentials (e.g., LOCAL and GOOGLE).
- `secret` must be hashed if `provider` is `LOCAL`.
