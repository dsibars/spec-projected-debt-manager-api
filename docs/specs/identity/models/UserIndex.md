# Model: User Index

## Goal
To maintain a global registry of all registered emails to ensure global uniqueness during registration and authentication.

## Properties
- `email`: `string` (Primary Key / Unique)
- `userId`: `uuid` (Link to the User)
- `createdAt`: `datetime`

## Constraints
- This model MUST reside in the **Global Identity Database**.
- It is the source of truth for "Email already in use" checks.
