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


