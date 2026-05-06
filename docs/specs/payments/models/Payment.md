# Model: Payment

Represents a financial transaction reducing a debt.

## Properties

- `id`: Unique identifier (String/UUID).
- `debtId`: Reference to [[specs/debts/models/Debt]].
- `amount`: Amount paid in smallest currency unit (Integer, Required).
- `notes`: Optional description (String).
- `paidAt`: Timestamp of the actual payment (DateTime).
- `createdAt`: System creation timestamp.

## Constraints
- `amount` must be greater than zero.
- `debtId` must refer to a valid, non-deleted Debt.
