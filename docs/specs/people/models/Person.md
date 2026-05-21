# Model: Person

This model represents an individual in the system.

## Properties

- `id`: Unique identifier (String/UUID).
- `tenantId`: The ID of the [[specs/identity/models/User]] who owns this record.
- `name`: Full name of the person (String, Required).
- `email`: Contact email ([[specs/shared/models/Email]], Optional).
- `phone`: Contact phone (String, Optional).
- `externalRef`: Optional reference to an external entity ID (e.g., User ID), used for linking.
- `isArchived`: Soft deletion flag (Boolean, Defaults to false).
- `createdAt`: Timestamp of creation (DateTime).
- `updatedAt`: Timestamp of last modification (DateTime).

## Constraints

- `name` cannot be empty or solely whitespace.
- `id` must be unique globally.
- `email` must be unique per `tenantId` if provided.
