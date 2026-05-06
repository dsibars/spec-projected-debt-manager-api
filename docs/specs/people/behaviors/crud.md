# Behaviors: People Management

This document defines the acceptance criteria for managing people via the API.

## Feature: Person Registration

### Scenario: Successful registration with full details
- **Given** no person exists with email "alice@example.com".
- **When** I request to create a person with name "Alice Smith" and email "alice@example.com".
- **Then** a new person record should be created in the Store.
- **And** the response should contain the generated ID.

### Scenario: Failing registration due to empty name
- **When** I request to create a person with an empty name.
- **Then** the system should return an `InvalidName` error.
- **And** no record should be stored.

## Feature: Person Retrieval & Discovery

### Scenario: Retrieving a person by ID
- **Given** a person exists with ID "uuid-1" and name "Bob".
- **When** I request to get the person with ID "uuid-1".
- **Then** the system should return the details for "Bob".

### Scenario: Listing people with pagination
- **Given** 25 active persons exist in the Store.
- **When** I request the first page of people with a size of 20.
- **Then** the system should return a list of 20 people.
- **And** the metadata should indicate 25 total items and 2 total pages.

## Feature: Person Lifecycle

### Scenario: Soft deleting a person (Archive)
- **Given** a person exists with ID "uuid-2".
- **When** I request to delete the person with ID "uuid-2".
- **Then** the person's `isArchived` flag should be set to `true`.
- **And** the person should not be returned in default list requests.
