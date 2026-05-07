# Model: User Aggregate

## Goal
To represent a unique identity within the system.

## Properties
- `id`: `uuid` (Primary Key, global `userId`)
- `tenantId`: `uuid` (Foreign Key to [[Tenant]], mandatory for data isolation)
- `email`: `string` (Unique)
- `isActive`: `boolean` (Default: `true`)
- `lastLoginAt`: `datetime?`
- `createdAt`: `datetime`
- `updatedAt`: `datetime`

---

# Model: Credential

## Goal
To store the secrets required for authentication.

## Properties
- `id`: `uuid`
- `userId`: `uuid` (Foreign Key to User)
- `provider`: `LOCAL | GOOGLE | APPLE`
- `secret`: `string` (Hashed password or external subject ID)
- `providerData`: `json?` (Extra data like Google profile info)
- `createdAt`: `datetime`
