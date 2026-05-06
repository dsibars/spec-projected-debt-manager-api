# Shared Implementation: Global Baselines

This document defines the base technical composition rules that apply to all modules in the system.

## Global Skill Assignments

Every module in the `docs/specs/` directory automatically inherits the following shared skills unless explicitly overridden in its own `implementation/` mapping:

1.  **Architecture**: `@shared/skills/patterns/simplified-ddd`
2.  **Code Quality**: `@shared/skills/patterns/clean-code`
3.  **DevOps**: `@shared/skills/devops/dockerization`
4.  **Testing**: `@shared/skills/testing/strategy`
5.  **Persistence**: 
    - @shared/skills/persistence/postgresql
    - @shared/skills/standards/data-formats
    - @shared/skills/persistence/migrations
6.  **Messaging**: `@shared/skills/messaging/rabbitmq`

## Technical Composition Rules

- **Zero Technical Leakage**: Spec files in `commands/`, `queries/`, and `models/` must remain purely declarative.
- **Idiomatic Source Root Mirroring**: The "Mirror Rule" dictates that `docs/specs/[module]/[layer]/[filename]` maps to the language's Idiomatic Source Root defined in its language skill file. 
  - For example, in Java, it maps to `src/main/java/{base_package}/[module]/[layer]/[filename].[ext]`.
- **Makefile Integrity**: Each implementation must provide a Makefile that supports `infra-up`, `build`, `test`, and `run` as defined in the DevOps and Testing skills.

## Universal Type Mapping
To ensure interoperability between different language implementations, the following mappings are mandated for the Builder:

| Spec Type | Java / Kotlin | Go | Rust |
| :--- | :--- | :--- | :--- |
| `String` | `String` | `string` | `String` / `&str` |
| `Integer` | `Long` (64-bit) | `int64` | `i64` |
| `Decimal` | `BigDecimal` | `decimal.Decimal` | `Decimal` |
| `Boolean` | `boolean` | `bool` | `bool` |
| `DateTime` | `OffsetDateTime` | `time.Time` | `DateTime<Utc>` |
| `UUID` | `java.util.UUID` | `uuid.UUID` | `uuid::Uuid` |

