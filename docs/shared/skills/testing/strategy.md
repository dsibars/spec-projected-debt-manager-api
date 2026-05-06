# Skill: Testing Strategy

This skill defines the architectural requirements and standards for testing all API implementations.

## Core Principles: The Testing Pyramid

All projects must follow the testing pyramid to ensure a balance between speed, isolation, and confidence.

### 1. Unit Tests (The Base)
- **Scope**: Individual components (use cases, domain entities).
- **Isolation**: Mandatory. All collaborators (repositories, external services) must be mocked.
- **Speed**: Must be extremely fast (millisecond range).
- **Goal**: Cover all edge cases and domain logic defined in `docs/specs/[module]/logic`.

### 2. Integration Tests (The Middle)
- **Scope**: Infrastructure components (repository implementations, external API clients).
- **Environment**: Requires the Dockerized environment defined in `@shared/skills/devops/dockerization` (e.g., Testcontainers).
- **Migration Execution**: Since ORM DDL auto-generation is banned, the test harness MUST orchestrate the execution of all schema migrations against the ephemeral database before executing the test suite.
- **Goal**: Verify that the technical implementation correctly interacts with the database (PostgreSQL) or external systems.

### 3. Application Tests (The Top)
- **Scope**: Full end-to-end functionality of a module.
- **Environment**: Requires a complete Dockerized environment.
- **Migration Execution**: Must ensure migrations are executed on startup, testing the identical boot sequence as production.
- **Testing Interface**: HTTP requests against the endpoints defined in `docs/specs/[module]/presentation/api.md`.
- **Goal**: Validate that all layers (presentation -> application -> domain -> infrastructure) work together to fulfill the specifications.

## Naming Convention

All test methods/cases must follow the behavioral naming pattern to ensure clarity and alignment with `docs/specs/[module]/behaviors`:

**Pattern**: `given[State]_when[Action]_then[ExpectedResult]`

- **Example**: `givenExistingPerson_whenUpdatingName_thenNameIsChanged()`
- **Example**: `givenEmptyDatabase_whenListingPeople_thenReturnsEmptyList()`

## Technical Requirements

- **Frameworks**: Use the standard testing framework for the target language (e.g., JUnit for Java/Kotlin, `go test` for Go, `cargo test` for Rust).
- **Docker Integration**: Higher-level tests must be orchestrated via the `Makefile` using the commands defined in `@shared/skills/devops/dockerization`.
- **Mocking**: Use language-appropriate mocking libraries (e.g., Mockito, MockK, GoMock).

## Testing Event-Driven Architectures (CQRS)

With the introduction of Eventual Consistency, the Builder MUST adhere to the following laws to prevent race conditions and non-deterministic failures in test suites:

### 1. Unit Testing Event Boundaries
- **Command Handlers**: Do not attempt to test end-to-end event propagation. The `EventBus` MUST be mocked. The test simply asserts that the domain state was altered AND that the correct `EventEnvelope` was dispatched to the mocked bus.
- **Event Handlers**: The test directly invokes the handler by passing a manually constructed `EventEnvelope`. Assert that the local Read Model or Domain Entity was correctly updated.

### 2. Integration / Application Testing (The Asynchronous Law)
- **Environment**: The Dockerized test harness MUST now spin up an ephemeral message broker (e.g., RabbitMQ via Testcontainers) alongside the database.
- **Asynchronous Assertions**: Application tests that trigger cross-module workflows (e.g., creating a Person and checking if Debts received it) MUST NOT use synchronous assertions. The test MUST utilize an asynchronous polling mechanism (e.g., `Awaitility` in Java) to wait for the Read Model to update before asserting or failing. 
  - *Example*: `await().atMost(5, SECONDS).untilAsserted(() -> assertThat(debtsPersonRepository.existsById(id)).isTrue());`
