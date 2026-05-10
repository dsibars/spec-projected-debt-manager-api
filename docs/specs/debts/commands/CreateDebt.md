# Use Case: Create Debt

## Goal
Initialize a new debt record in the user's ledger.

## Input
- `tenantId`: UUID (Injected from Auth context)
- `personId`: UUID
- `name`: String
- `totalAmount`: Integer
- `direction`: OWED_TO_ME | I_OWE
- `currency`: String? (Defaults to "USD")
- `dueDate`: DateTime?

## Flow
1. Validate that `totalAmount` is greater than 0.
2. If `currency` is null or empty, set `currency` to "USD".
3. Validate that `personId` exists by querying the local [[projections/PersonReadModel]].
4. Validate that the person is not archived.
5. Generate a unique `id`.
6. Create the [[models/Debt]] record with the provided `tenantId`.
7. Initialize the [[projections/DebtSummaryProjection]] including the `tenantId`.
8. Persist both the Aggregate and the Projection (Atomic Inline Update).
9. Emit `DebtRegistered` event (see [[events/DebtRegistered]]).
10. Return `id`.

## Emits
- `DebtRegistered` — see [[events/DebtRegistered]]

## Errors
- `InvalidAmount`: If `totalAmount <= 0`.
- `PersonNotFound`: If `personId` does not exist in the local PersonReadModel.
- `PersonArchived`: If the referenced person is archived.
- `CurrencyNotSupported`: If the provided currency code is not recognized by `@shared/skills/standards/data-formats`.
