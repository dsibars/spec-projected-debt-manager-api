# Use Case: Create Debt

## Goal
Initialize a new debt record in the user's ledger.

## Input
- `tenantId`: `uuid` (Injected from Auth context)
- `personId`: `uuid`
- `name`: `string`
- `totalAmount`: `integer`
- `direction`: `OWED_TO_ME | I_OWE`
- `currency`: `string?`
- `dueDate`: `datetime?`

## Flow
1. Validate that `personId` exists in the [[projections/PersonReadModel]] (Note: This queries the **READ_DB**, which is eventually consistent).
2. Validate that the person is not archived.
3. Generate a unique `id`.
4. Create the [[models/Debt]] record with the provided `tenantId`.
5. Initialize the [[projections/DebtSummaryProjection]] including the `tenantId`.
6. Persist both the Aggregate and the Projection.
7. Emit `DebtRegistered` event (carrying `tenantId` in envelope).
8. Return `id`.

## Errors
- `PersonNotFound`: If `personId` is invalid or belongs to another user.
