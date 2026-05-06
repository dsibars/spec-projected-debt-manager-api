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
- **Environment**: Requires the Dockerized environment defined in `@shared/skills/devops/dockerization`.
- **Goal**: Verify that the technical implementation correctly interacts with the database (PostgreSQL) or external systems.

### 3. Application Tests (The Top)
- **Scope**: Full end-to-end functionality of a module.
- **Environment**: Requires a complete Dockerized environment.
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
