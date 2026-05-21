# Skill: Messaging - RabbitMQ

## Category: messaging
## Provides:
- Rabbitmq
## Conflicts With:
- contracts
## Depends On:
- None explicitly declared


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

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Broker URL**: `messaging.url`, `messaging.user`, `messaging.password`

## Connection Credentials (Local Dev)
- **Host**: `localhost` (or `broker` within docker-compose).
- **Port**: `5672` (AMQP) / `15672` (Management UI).
- **User/Password**: `guest` / `guest`.