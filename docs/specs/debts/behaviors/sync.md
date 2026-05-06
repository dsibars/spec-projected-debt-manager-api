# Behavior: Data Synchronization (Hexagonal)

This document verifies the eventual consistency behaviors between modules.

## Feature: Person Read Model Sync

### Scenario: Successful synchronization of a new person
- **Given** no record exists in `debts.PersonReadModel` for ID "uuid-p1".
- **When** a `people.PersonCreated` event is received with ID "uuid-p1" and Name "Charlie".
- **Then** a new record should be created in the `debts.PersonReadModel` for "uuid-p1".
- **And** the name should be "Charlie".
- **And** `isArchived` should be `false`.

### Scenario: Synchronizing a person archival
- **Given** a record exists in `debts.PersonReadModel` for ID "uuid-p1" with `isArchived: false`.
- **When** a `people.PersonArchived` event is received with ID "uuid-p1".
- **Then** the record for "uuid-p1" in `debts.PersonReadModel` should have `isArchived` set to `true`.

## Feature: Payment Impact Sync

### Scenario: Updating debt balance from payment
- **Given** a debt exists with ID "uuid-d1" and `currentBalance: 1000`.
- **When** a `payments.PaymentRegistered` event is received for Debt "uuid-d1" with Amount `300`.
- **Then** the `currentBalance` for Debt "uuid-d1" should be updated to `700`.
- **And** if the balance becomes 0, the `isSettled` flag should be `true`.
