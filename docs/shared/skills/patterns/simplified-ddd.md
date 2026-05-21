# Skill: Simplified DDD Approach

## Category: patterns
## Provides:
- Simplified Ddd
## Conflicts With:
- clean-code
- cqrs-and-events
- data-replication
- module-boundaries
- outbox-inbox-schema
- resilience
- tenant-load-balancing
## Depends On:
- None explicitly declared


This skill defines the architectural patterns for a "Simplified Domain Driven Design" (DDD).

## Core Principles

1.  **Ubiquitous Language**: Matching terms from `docs/specs/[module]/definitions`.
2.  **Hexagonal Structure**: `domain/`, `application/`, `presentation/`, `infrastructure/`.

3.  **Law of Optimistic Locking**:
    - **Aggregates**: MUST include a `version` (Integer).
    - **Projections (Inline)**: Any projection updated via the "Inline Update" pattern MUST also include a `version` field to prevent race conditions during atomic dual-writes.
    - Repositories MUST enforce `WHERE version = :oldVersion` and throw `ConcurrencyConflict` on failure.

4.  **Binary Structural Law**:
    - To support separate REST and Worker binaries, the project structure MUST decouple the "Runners" from the "Logic".
    - **ApiRunner**: Bootstraps the application with `presentation/rest/` adapters enabled.
    - **WorkerRunner**: Bootstraps the application with `presentation/subscribers/` adapters enabled.
    - Both runners share the same `application/` and `domain/` layers.

5.  **Multi-Tenancy Isolation Law**: 
    - Functionally: `tenantId` == `userId`.
    - Routing: `tenantId` extracted from the JWT `sub` claim for data isolation.

6.  **Persistence Segregation Law**:
    - Primary schema (Aggregates) vs Projection schema (Read Models) within the same database instance.
    - No physical coupling (FKs) between Write and Read sides.

## 7. Event-First Design Law
- Every state change in an Aggregate MUST produce at least one Domain Event.
- Events are the source of truth for cross-module communication.
- Aggregates MUST NOT directly call other aggregates. They emit events instead.
- The `events/` folder in each module is a first-class spec layer.
- Event consumers MUST be idempotent.
- Event producers MUST use the Outbox pattern for atomic consistency.

## 8. Aggregate Isolation Law
- Aggregates MUST NOT hold direct references to other aggregate roots.
- Cross-aggregate relationships MUST use foreign identity (UUID) only.
- Cross-aggregate consistency MUST be eventual, driven by events.