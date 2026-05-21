# Model: Debt

Represents a financial obligation.

## Properties

- `id`: Unique identifier (String/UUID).
- `tenantId`: The ID of the [[specs/identity/models/User]] who owns this record.
- `personId`: Reference to [[specs/people/models/Person]].
- `name`: Short description or reason for the debt (String, Required).
- `totalAmount`: Original amount in smallest currency unit (Integer, e.g., cents, Required).
- `currentBalance`: Remaining amount in smallest currency unit (Integer, Required).
- `currency`: Currency code ([[specs/shared/models/Currency]], Defaults to "USD").
- `direction`: The debt flow, either `OWED_TO_ME` or `I_OWE` (Enum, Required).
- `dueDate`: Optional deadline for settlement (DateTime).
- `isArchived`: Flag for soft deletion (Boolean, Default False).
- `createdAt`: Timestamp.
- `updatedAt`: Timestamp.

## Constraints
- `totalAmount` must be greater than zero.
- `currentBalance` must be between 0 and `totalAmount`.
- `personId` must refer to an existing `PersonReadModel` in the local integration store (replicated from the People module).
