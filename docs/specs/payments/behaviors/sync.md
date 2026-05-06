# Behavior: Data Synchronization (Hexagonal)

## Feature: Debt Read Model Sync

### Scenario: Syncing a new debt for payment validation
- **Given** no record exists in `payments.DebtReadModel` for ID "uuid-d2".
- **When** a `debts.DebtRegistered` event is received with ID "uuid-d2" and `currentBalance: 5000`.
- **Then** a new record should be created in the `payments.DebtReadModel` for "uuid-d2".
- **And** the balance should be `5000`.
- **And** `isSettled` should be `false`.

### Scenario: Syncing debt settlement
- **Given** a record exists in `payments.DebtReadModel` for ID "uuid-d2" with `isSettled: false`.
- **When** a `debts.DebtSettled` event is received for ID "uuid-d2".
- **Then** the record for "uuid-d2" in `payments.DebtReadModel` should have `isSettled` set to `true`.
