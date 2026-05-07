# Skill: Messaging - RabbitMQ

This skill defines the technical implementation for a distributed, highly available, asynchronous messaging broker using RabbitMQ.

## Technical Requirements

*   **Transport Mechanism**: External RabbitMQ broker.
*   **Transactionality (Eventual Consistency)**: 
    *   Events are published asynchronously. 
    *   Subscribers execute in separate transactions (often in entirely separate microservice instances).
    *   To prevent message loss if the database commits but the broker is down, the implementation MUST use the **Outbox Pattern**.
    *   **Outbox Implementation**: The Builder must implement a Polling Worker (e.g., a background scheduler) that reads unpublished events from a local `outbox_events` table and dispatches them to RabbitMQ, marking them as processed only upon broker acknowledgment.
*   **Routing & Topology**:
    *   **Exchange**: Use a `Topic` exchange for Domain Events (e.g., `spd.domain.events`).
    *   **Tenant Isolation**: While a single RabbitMQ cluster serves all tenants, isolation is achieved via routing.
    *   **Routing Keys**: MUST include the `tenantId` to allow for granular filtering. Format: `[tenantId].[module].[entity].[action]` (e.g., `550e8400.people.person.created`).
    *   **Queues**: Named per subscriber logic AND tenant if necessary. For global subscribers, use wildcards (e.g., `*.people.person.created`).
*   **Serialization**: All payloads must be serialized in UTF-8 JSON.
*   **Resilience**: Dead Letter Queues (DLQ) are mandatory for all event subscriptions.

## Use Case
This implementation must be projected when the architecture targets a distributed microservices environment.
