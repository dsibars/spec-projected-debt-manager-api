# Model: User Index

## Goal
To maintain a global, cross-shard registry of all registered emails to ensure global uniqueness and facilitate shard discovery during authentication.

## Properties
- `email`: `string` (Primary Key / Unique)
- `userId`: `uuid` (Link to the User in their respective Shard)
- `shardId`: `uuid` (The physical location of the User's data)
- `createdAt`: `datetime`

## Constraints
- This model MUST reside in the **Global Identity Database**.
- It is the source of truth for "Email already in use" checks.
