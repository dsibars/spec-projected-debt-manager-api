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
    *   **Outbox Implementation**: The Builder must implement a Polling Worker that reads unpublished events from a local `outbox_events` table and dispatches them to RabbitMQ, marking them as processed only upon broker acknowledgment.
*   **Routing & Topology**:
    *   **Exchange**: Use a `Topic` exchange for Domain Events (e.g., `spd.domain.events`).
    *   **Tenant Isolation**: While a single RabbitMQ cluster serves all tenants, isolation is achieved via routing.
    *   **Routing Keys**: MUST include the `tenantId` to allow for granular filtering. Format: `[tenantId].[module].[entity].[action]` (e.g., `550e8400.people.person.created`).
    *   **Queues**: Named per subscriber logic AND tenant if necessary. For global subscribers, use wildcards (e.g., `*.people.person.created`).
*   **Serialization**: All payloads must be serialized in UTF-8 JSON.
*   **Resilience**: Dead Letter Queues (DLQ) are mandatory for all event subscriptions.

## Per-Platform Outbox Poller & Consumer

The Outbox Poller and AMQP Consumer are platform-specific background tasks. They run in the `worker` binary.

| Concern | Java / Spring | Kotlin / Ktor | Go / Gin | Rust / Axum |
|---|---|---|---|---|
| **Outbox Poller** | `@Scheduled(fixedRate = 5000)` or `TaskScheduler` | `Coroutine` with `delay(5000)` loop | `time.Ticker` in a goroutine | `tokio::spawn` + `tokio::time::interval(Duration::from_secs(5))` |
| **Poll Query** | `SELECT * FROM outbox_events WHERE processed = false ORDER BY created_at LIMIT 100` | Same | Same | Same |
| **Publish Ack** | `rabbitTemplate.convertAndSend()` + `UPDATE outbox_events SET processed = true` | Same logic via AMQP client | `channel.Publish()` + DB update | `basic_publish()` + DB update |
| **AMQP Consumer** | `@RabbitListener(queues = "...")` | `RabbitMQ` client coroutine consumer | `amqp091-go` `channel.Consume()` | `lapin` `BasicConsumeOptions` + `tokio::spawn` |
| **Consumer Threading** | Spring AMQP listener container (thread pool) | Coroutine `launch` on `Dispatchers.IO` | One goroutine per consumer | One `tokio::task` per consumer |
| **Error Handling** | `ImmediateRequeueMessage` → DLQ after N retries | Manual NACK with requeue=false | Manual NACK | Manual NACK |

### Outbox Polling Rules
1. **Frequency**: Poll every 5 seconds (configurable).
2. **Batch Size**: Fetch up to 100 unpublished events per poll.
3. **Ordering**: Process events in `created_at` ascending order to preserve causal ordering within a tenant.
4. **Idempotency**: The consumer's Inbox Pattern handles duplicate deliveries. The poller MUST NOT skip events based on deduplication.
5. **Failure Handling**: If publishing to RabbitMQ fails, the event MUST remain `processed = false` and be retried on the next poll cycle.

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Broker URL**: `messaging.url`, `messaging.user`, `messaging.password`

## Connection Credentials (Local Dev)
- **Host**: `localhost` (or `broker` within docker-compose).
- **Port**: `5672` (AMQP) / `15672` (Management UI).
- **User/Password**: `guest` / `guest`.
