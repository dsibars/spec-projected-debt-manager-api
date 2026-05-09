# Use Case: Create Debt

## Goal
Initialize a new debt record in the user's ledger.

## Input
- `tenantId`: `uuid` (Injected from Auth context)
- `personId`: `uuid`
- `name`: `string`
- `totalAmount`: `integer`
- `direction`: `OWED_TO_ME | I_OWE`
- `currency`: `string?` (Defaults to "USD")
- `dueDate`: `datetime?`

## Flow
1. Validate that `totalAmount` is greater than 0.
2. If `currency` is null or empty, set `currency` to "USD".
3. Validate that `personId` exists in the [[projections/PersonReadModel]].
4. Validate that the person is not archived.
5. Generate a unique `id`.
6. Create the [[models/Debt]] record with the provided `tenantId`.
7. Initialize the [[projections/DebtSummaryProjection]] including the `tenantId`.
8. Persist both the Aggregate and the Projection (Atomic Inline Update).
9. Emit `DebtRegistered` event.
10. Return `id`.

## Errors
- `InvalidAmount`: If `totalAmount <= 0`.
- `PersonNotFound`: If `personId` is invalid or belongs to another user.
- `PersonArchived`: If the referenced person is archived.
- `CurrencyNotSupported`: If the provided currency code is not recognized by `@shared/skills/standards/data-formats`.
