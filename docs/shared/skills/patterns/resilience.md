# Skill: Resilience & Consistency Patterns

This skill defines the laws for maintaining data integrity and recovering from failures in a distributed, sharded ecosystem.

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

## 3. Shard-Aware Routing Law
- Repositories MUST be aware of the `shardId` provided in the context.
- In a multi-database environment, the `ShardPort` is responsible for selecting the correct database connection/pool based on the `shardId`.
