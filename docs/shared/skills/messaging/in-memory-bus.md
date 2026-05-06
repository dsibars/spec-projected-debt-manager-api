# Skill: Messaging - In-Memory Bus

This skill defines the technical implementation for a synchronous, single-node messaging transport mechanism. It is ideal for monolithic deployments or "dummy" prototypes where the complexity of an external broker is not yet required.

## Technical Requirements

*   **Transport Mechanism**: Execution occurs within the same memory space as the application (e.g., Spring's `ApplicationEventPublisher`, Guava `EventBus`, or a custom Mediator implementation).
*   **Transactionality**: 
    *   By default, Event Listeners/Subscribers execute synchronously within the same database transaction as the emitting Command.
    *   If a subscriber fails, the entire transaction (including the original command's state change) rolls back.
*   **Routing**:
    *   `CommandBus`: Routes to exactly one handler.
    *   `QueryBus`: Routes to exactly one handler.
    *   `EventBus`: Routes to zero or more subscribers.

## Use Case
This implementation is the default for local development and initial Spec-Projected synchronizations unless a distributed architecture is explicitly requested.
