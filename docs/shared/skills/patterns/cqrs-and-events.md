# Skill: CQRS and Event-Driven Architecture

This skill defines the technical laws for implementing Command Query Responsibility Segregation (CQRS) and inter-module communication via Eventual Consistency.

## 1. Strict CQRS Laws
The Application Layer must strictly segregate intent into Commands and Queries.

*   **Commands**:
    *   **Intent**: Alter the state of the system (e.g., Create, Update, Delete).
    *   **Return Type**: Strict CQRS is enforced. Commands MUST return `void` (or at most, an identifier of the created resource). They must never return full Domain Entities or DTOs.
    *   **Routing**: Must be dispatched via a `CommandBus` to exactly one `CommandHandler`.
*   **Queries**:
    *   **Intent**: Read the state of the system. MUST NOT produce any side effects.
    *   **Routing**: Must be dispatched via a `QueryBus` to exactly one `QueryHandler`.

## 2. The Decoupling Law (Banning Synchronous Calls & Model Sharing)
*   **Cross-Module Execution Independence**: A module (e.g., `Debts`) is **strictly forbidden** from invoking the Use Cases, Command Handlers, or Query Handlers of another module (e.g., `People`) synchronously. No direct code injection across module boundaries.
*   **Bounded Context Isolation**: Modules MUST NOT import or reference Domain Models from other modules. A `Person` in the `People` module is completely disjoint from a `PersonReadModel` in the `Debts` module. They are two distinct files, representations, and concepts. Sharing domain classes across modules is strictly banned.

## 3. Domain Events and Data Replication
To maintain high availability and decoupling, the system uses Eventual Consistency via Domain Events. For detailed rules on data replication, see `@shared/skills/patterns/data-replication`.

*   **Event Emission**: Any Command that successfully alters domain state MUST emit a Domain Event (e.g., `PersonCreated`, `DebtSettled`) to an `EventBus`.
*   **Event Subscription (Read Models)**:
    *   If Module B requires data owned by Module A, Module B must subscribe to Module A's Domain Events.
    *   **Payload Law**: Every Event Subscriber MUST receive the **`EventEnvelope`** (as defined in `specs/shared/models/EventEnvelope.md`) as its input. The Subscriber MUST NOT expect the raw domain payload alone.
    *   **Tenant Filtering Law**: Before processing an event, the Subscriber MUST verify that the `tenantId` in the `EventEnvelope.metadata` matches a valid tenant it is responsible for. This ensures that even with shared infrastructure, cross-tenant data leakage is prevented at the application level.
    *   Module B must use the `payload` portion of the envelope to construct its own isolated "Read Model" of the data.
    *   **Idempotency Law (Inbox Pattern)**: Because distributed messaging systems guarantee *at-least-once* delivery, every Event Subscriber MUST be strictly idempotent. 
        *   Before processing the event logic, the implementation MUST check if the `eventId` from the `EventEnvelope` already exists in the `processed_events` table (Inbox Pattern).
        *   If it exists, the event MUST be discarded (ACK'd but not processed).
        *   If it does not exist, the `eventId` must be stored in the same transaction as the Read Model update.
    *   **Read Model Law (Immutability)**: Read Models MUST be treated as strictly read-only by the Logic layer. They are projections of external state and MUST ONLY be updated by Event Handlers in response to Domain Events. Manual modification of a Read Model by a Command Handler is strictly forbidden.
*   **The Hydration Protocol (Cold Starts)**: 
    *   If a module boots with an empty Read Model, it cannot process new commands.
    *   To solve this, the module MUST emit a "Backfill Request" Command to the Event Bus (e.g., `RequestPersonBackfill`).
    *   The owning module (e.g., `People`) subscribes to this request, queries its entire database, and re-publishes the creation events (e.g., `PersonCreated`) for all active records.
    *   The requesting module uses its existing Event Handlers to ingest this historical data idempotently.

## 4. Specification Syntax
When writing declarative logic specs in `docs/specs/[module]/logic/`, use the following sections:
- `## Emits`: List the events triggered by the command.
- `## Subscribes To`: List the external events this handler listens to.
