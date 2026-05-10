# Behaviors: Debt Management

## Feature: Debt Lifecycle

### Scenario: Creating a debt for an existing person
- **Given** a person with ID "person-1" exists.
- **When** I request to create a debt for "person-1" with name "Lunch" and totalAmount 1500.
- **Then** a new debt should be created.
- **And** its currentBalance should be 1500.

### Scenario: Registering a partial payment
- **Given** a debt exists with ID "debt-1" and currentBalance 1500.
- **When** I register a payment for "debt-1" with amount 500.
- **Then** a new payment record should be created.
- **And** the debt's currentBalance should become 1000.

### Scenario: Settling a debt with full payment
- **Given** a debt exists with ID "debt-2" and currentBalance 1000.
- **When** I register a payment for "debt-2" with amount 1000.
- **Then** the debt's currentBalance should become 0.

### Scenario: Preventing overpayment
- **Given** a debt exists with ID "debt-3" and currentBalance 500.
- **When** I attempt to register a payment for "debt-3" with amount 600.
- **Then** the system should return a `PaymentExceedsBalance` error.
