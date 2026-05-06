# Behaviors: Payment Registration

## Feature: Payment Lifecycle

### Scenario: Registering a valid payment
- **Given** a debt exists in the `debts` module with ID "d-1" and currentBalance 1000.
- **When** I register a payment for "d-1" with amount 400.
- **Then** a new payment should be recorded in the `payments` module.
- **And** the debt "d-1" in the `debts` module should have its balance updated to 600.

### Scenario: Settling a debt via final payment
- **Given** a debt exists in the `debts` module with ID "d-2" and currentBalance 500.
- **When** I register a payment for "d-2" with amount 500.
- **Then** the debt "d-2" should have its balance updated to 0.
- **And** the debt "d-2" should be marked as `isSettled`.

### Scenario: Preventing overpayment
- **Given** a debt exists with ID "d-3" and currentBalance 200.
- **When** I attempt to register a payment for "d-3" with amount 300.
- **Then** the system should return a `PaymentExceedsBalance` error.
- **And** no payment record should be created.
