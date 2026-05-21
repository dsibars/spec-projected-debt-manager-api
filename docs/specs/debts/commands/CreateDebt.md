# Command: Create Debt

## Goal
Initialize a new debt record in the user's ledger.

## Input
- `tenantId`: UUID (Injected from Auth context)
- `personId`: UUID
- `name`: String
- `totalAmount`: Integer
- `direction`: `OWED_TO_ME` | `I_OWE`
- `currency`: String? (Defaults to "USD")
- `dueDate`: DateTime?

## Preconditions
- `totalAmount` must be greater than 0.
- `personId` must refer to an existing `PersonReadModel` in the local integration store.
- The referenced `PersonReadModel` must not be archived.

## Flow
1. If `currency` is null or empty, set `currency` to "USD".
2. Validate that `currency` is recognized by `@shared/skills/standards/data-formats`.
3. Generate a unique `id`.
4. Create the [[models/Debt]] record with the provided `tenantId`.
5. Persist the Aggregate.
6. Emit `DebtRegistered` event (see [[events/DebtRegistered]]).

## Postconditions
- A `Debt` aggregate exists with `currentBalance == totalAmount`.
- The debt will appear in read models via eventual consistency.

## Effects
- Emits: [[events/DebtRegistered]]

## Errors
- `InvalidAmount`: If `totalAmount <= 0`.
- `PersonNotFound`: If `personId` does not exist in the local Person integration store.
- `PersonArchived`: If the referenced person is archived.
- `CurrencyNotSupported`: If the provided currency code is not recognized.

## Result
- `id`: UUID of the created debt.
