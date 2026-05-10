# Shared Implementation: Global Baselines

This document defines the base technical composition rules that apply to all modules in the system.

## Global Skill Assignments

Every module in the `docs/specs/` directory automatically inherits the following shared skills unless explicitly overridden in its own `implementation/` mapping:

1.  **Architecture**: `@shared/skills/patterns/simplified-ddd`
2.  **Code Quality**: `@shared/skills/patterns/clean-code`
3.  **DevOps**: 
    - `@shared/skills/devops/dockerization`
    - `@shared/skills/devops/configuration-management`
    - `@shared/skills/devops/health-probes`
4.  **Testing**: `@shared/skills/testing/strategy`
5.  **Persistence**: 
    - @shared/skills/persistence/postgresql
    - @shared/skills/standards/data-formats
    - @shared/skills/persistence/migrations
    - @shared/skills/persistence/read-write-split
    - @shared/skills/persistence/repository-pattern
6.  **Messaging**: 
    - `@shared/skills/messaging/rabbitmq`
    - `@shared/skills/messaging/contracts`
7.  **Security**:
    - `@shared/skills/security/jwt`
    - `@shared/skills/security/hashing`
    - `@shared/skills/security/oauth-integration`
    - `@shared/skills/security/pii-management`
    - `@shared/skills/security/traffic-governance`
8.  **Scaling**:
    - `@shared/skills/patterns/tenant-load-balancing`
9.  **Resilience**:
    - `@shared/skills/patterns/resilience`
    - `@shared/skills/patterns/outbox-inbox-schema`
10. **Observability**:
    - `@shared/skills/observability/opentelemetry`
    - `@shared/skills/observability/signoz`
11. **Meta**:
    - `@shared/skills/meta/type-mapping`
    - `@shared/skills/patterns/module-boundaries`
12. **Testing**:
    - `@shared/skills/testing/behavior-projection`
13. **Presentation**:
    - `@shared/skills/presentation/rest-api`

## Technical Composition Rules

- **Zero Technical Leakage**: Spec files in `commands/`, `queries/`, and `models/` must remain purely declarative.
- **Security Context Injection**: To ensure multi-tenant isolation, the Presentation layer MUST extract the `sub` (userId) and `sid` (shardId) claims from the JWT and inject them into every Command and Query as a mandatory `Context` argument. Use Cases MUST NOT perform manual token parsing; they should receive pre-validated context. In this multi-tenant architecture, the `tenantId` is functionally equivalent to the `userId` (the JWT `sub` claim).
- **Projection Consistency Policy**: To balance UX performance and system complexity, a hybrid strategy is used:
  - **Inline Updates (Atomic)**: Primary read models within the same module (e.g., `DebtSummary`) MUST be updated within the same transaction as the command.
  - **Event-Driven Updates (Eventual)**: Cross-module read models or non-critical views MUST be updated via event subscribers.
- **Shard Maintenance Enforcement**: Before executing any write operation (Commands), the system MUST verify the `status` of the shard identified by `shardId` in the `Context`. If the status is `MAINTENANCE`, the operation MUST be aborted with a `ShardUnderMaintenance` error.
- **Idiomatic Source Root Mirroring**: The "Mirror Rule" dictates that `docs/specs/[module]/[layer]/[filename]` maps to the language's Idiomatic Source Root defined in its language skill file. 
  - For example, in Java, it maps to `src/main/java/{base_package}/[module]/[layer]/[filename].[ext]`.
- **Makefile Integrity**: Each implementation must provide a Makefile that supports `infra-up`, `build`, `test`, and `run` as defined in the DevOps and Testing skills.

## Event-Driven Architecture Rule
- All modules participating in cross-module workflows MUST declare their events in `events/`.
- All events must comply with the global rules defined in [[events]].
- Commands that produce side effects in other modules MUST do so via events, not direct calls.
- The Builder MUST generate Outbox table entries for all event producers.

## Universal Type Mapping
To ensure interoperability between different language implementations, the mapping defined in `@shared/skills/meta/type-mapping` MUST be followed by the Builder.
