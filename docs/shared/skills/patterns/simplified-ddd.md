# Skill: Simplified DDD Approach

This skill defines the architectural patterns for a "Simplified Domain Driven Design" (DDD) to be applied across all implementations.

## Core Principles

1.  **Ubiquitous Language**: All technical identifiers (classes, variables, tables) must match the terms defined in `docs/specs/[module]/definitions`.
2.  **Layers**:
    *   **Domain Layer**: Pure business logic and entities. No dependencies on frameworks or external libraries.
    *   **Application Layer**: Use cases and orchestration. Depends only on the Domain Layer.
    *   **Infrastructure Layer**: Persistence, external APIs, and technical implementation details.
3.  **Encapsulation**:
    *   Entities must maintain their own consistency.
    *   Value Objects are preferred for attributes with complex validation logic.
4.  **Repositories**:
    *   Interface defined in the Domain/Application layer.
    *   Implementation provided by the Infrastructure layer.

## Implementation Rules

*   Each `specs/[module]/logic` file corresponds to a single use case / application service.
*   Each `specs/[module]/models` file corresponds to a domain entity or aggregate.
