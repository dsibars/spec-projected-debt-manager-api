# Skill: Testing Strategy

This skill defines the architectural requirements and standards for testing all API implementations.

## Core Principles: The Testing Pyramid
1. **Unit Tests**: Fast, isolated (mocked), covering domain logic.
2. **Integration Tests**: Infrastructure-focused (DB, Broker), requires Docker.
3. **Application Tests**: End-to-end HTTP flows against the projected API.

## Testing Event-Driven Architectures (CQRS)
To prevent non-deterministic failures in test suites, the Builder MUST adhere to:

### 1. Asynchronous Assertion Strategy
Application tests that trigger cross-module workflows MUST NOT use synchronous assertions.
- **Polling**: Use an asynchronous polling mechanism (e.g., `Awaitility`).
- **Timeout Law**:
    - **Default Timeout**: 5 seconds.
    - **Poll Interval**: 100 milliseconds.
- **Fail Fast**: If the condition is not met within the timeout, the test MUST fail with a clear "Eventual Consistency Failure" message.

### 2. Idempotency Verification
Every integration test for an Event Subscriber MUST execute the same event TWICE to verify that the **Inbox Pattern** (Idempotency) is correctly implemented and doesn't create duplicate side effects.

## Naming Convention
`given[State]_when[Action]_then[ExpectedResult]`

## Technical Requirements
- **Frameworks**: Native language test runners.
- **Docker Integration**: Orchestrated via `Makefile` and Testcontainers.
