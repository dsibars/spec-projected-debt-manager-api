# Model: Person

This model represents an individual in the system.

## Properties

- `id`: Unique identifier (String/UUID).
- `name`: Full name of the person (String, Required).
- `email`: Contact email (String, Optional, Must be valid format if provided).
- `phone`: Contact phone (String, Optional).
- `isArchived`: Soft deletion flag (Boolean, Defaults to false).
- `createdAt`: Timestamp of creation (DateTime).
- `updatedAt`: Timestamp of last modification (DateTime).

## Constraints

- `name` cannot be empty or solely whitespace.
- `id` must be unique across all persons.
- `email` must be unique if provided and not null.
