# Skill: Resilience & Consistency Patterns

## Category: patterns
## Provides:
- Resilience
## Conflicts With:
- clean-code
- cqrs-and-events
- data-replication
- module-boundaries
- outbox-inbox-schema
- simplified-ddd
- tenant-load-balancing
## Depends On:
- None explicitly declared


This skill defines the laws for maintaining data integrity and recovering from failures in a distributed, multi-tenant ecosystem.

## 1. Transactional Outbox Law
To prevent data loss between the Database and the Message Broker:
- Every command that modifies state and emits an event MUST use a **Transactional Outbox**.
- The state change and the event insertion into an `outbox` table MUST happen in the same database transaction.
- A background process (Relay) is responsible for publishing events from the `outbox` table to the Broker.
- Refer to `@shared/skills/patterns/outbox-inbox-schema` for the table definition.

## 2. Eventual Consistency & Repair Law
Projections are eventually consistent. If events are lost or a new projection is added:
- Every module MUST provide a "Replay" command (e.g., `ReplayActivePeople`) that emits current-state events for a specific `tenantId`.
- Subscribers MUST implement the **Idempotent Consumer** pattern using an `inbox` table to prevent duplicate processing.

## 3. Circuit Breaker Law
- External service calls (e.g., email providers, payment gateways) MUST be wrapped in circuit breakers.
- After a configurable failure threshold, the circuit MUST open and fail fast.
- When the circuit is open, the system SHOULD degrade gracefully (e.g., queue the operation for retry).