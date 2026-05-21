# Skill: Data Replication & Read Models

## Category: patterns
## Provides:
- Data Replication
## Conflicts With:
- clean-code
- cqrs-and-events
- module-boundaries
- outbox-inbox-schema
- resilience
- simplified-ddd
- tenant-load-balancing
## Depends On:
- None explicitly declared


This skill defines the strategy for cross-module data consistency using Event-Driven Data Replication and Read Models.

## 1. Architectural Philosophy
To maintain strict isolation between modules (Bounded Contexts) and avoid synchronous cross-service dependencies, we use **Event-Driven Data Replication**.

- **Autonomy**: Each module must have all the data it needs to perform its business logic locally.
- **Latency**: Data is served from local "Read Models" (projections) to ensure high performance.
- **Consistency**: The system achieves eventual consistency by propagating changes via Domain Events.

## 2. Read Models (Projections)
A **Read Model** is a local, denormalized projection of data owned by an external module.

- **Storage**: Read Models are stored in the database schema of the consuming module.
- **Tenant Isolation**: Every Read Model MUST include a `tenant_id` column. Data replication is tenant-scoped; a module MUST only replicate data belonging to the same tenant as the original event.
- **Naming Convention**:
    - Table name: `[external_entity]_read_model` (e.g., `person_read_model` inside the `debts` schema).
    - Foreign Keys: Local tables should reference the Read Model's ID (e.g., `debt.person_id` -> `person_read_model.id`).
- **Isolation**: The consuming module **MUST NEVER** perform direct writes to a Read Model from its Command Handlers. Read Models are exclusively updated by Event Subscribers.

## 3. The Replication Flow
1.  **Change in Owner Module**: A Command in the "Owner" module (e.g., `People`) updates its Domain Model and emits a Domain Event (e.g., `PersonCreated`).
2.  **Event Propagation**: The event is published to the Messaging Infrastructure (e.g., RabbitMQ).
3.  **Consumption in Dependent Module**: The "Consumer" module (e.g., `Debts`) subscribes to the event.
4.  **Local Update**: An Event Subscriber in the Consumer module receives the event and updates its local Read Model.

## 4. Backfill & Hydration (Cold Starts)
When a new module is introduced or a database is wiped, Read Models must be populated from existing data.

- **Request**: The Consumer module emits a `BackfillRequested` event.
- **Response**: The Owner module listens for this request and re-publishes the creation events for all its active domain entities.
- **Idempotency**: All Event Subscribers MUST implement the **Inbox Pattern** (as defined in `@shared/skills/patterns/cqrs-and-events`) to handle duplicate events safely during backfills.

## 5. Schema Separation
In PostgreSQL, this is enforced using schemas. For example, the `debts` module might have:
- `debts.debts` (Local Domain Table)
- `debts.person_read_model` (Replicated Data from People module)

This ensures that the `debts` module's code only ever interacts with the `debts` schema, maintaining the illusion of a single, unified database while being physically and logically decoupled.