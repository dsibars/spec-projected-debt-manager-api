# Language Skill: Kotlin

## Category: languages
## Provides:
- Kotlin
## Conflicts With:
- go
- java
- rust
## Depends On:
- None explicitly declared


## Standards
- **Version**: Kotlin 1.9.22+.
- **Runtime**: JVM 21.
- **Style Guide**: Follow [Official Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html).
- **Linting**: `ktlint` is MANDATORY in CI.

## Best Practices
- **Immutability**: Use `val` by default. Use `data class` for domain models and DTOs.
- **Null Safety**: Leverage Kotlin's type system to eliminate `NullPointerException`.
- **Coroutines**: Use structured concurrency for all asynchronous operations. Command and Query handlers SHOULD be `suspend` functions.
- **Type Mapping Law**: Any property marked as optional (e.g., `email?`) or nullable in the specifications MUST be projected as a nullable type `T?` in Kotlin to leverage the language's native null-safety features.
- **Functional Idioms**: Prefer `map`, `filter`, `fold` over imperative loops.
- **Extension Functions**: Use to add utility logic to classes without inheritance.
- **Sealed Classes**: Use sealed classes / sealed interfaces for Domain Error hierarchies to enable exhaustive `when` expressions.

## Type Mapping Law

| Spec Type | Kotlin Type | Notes |
|---|---|---|
| `String` | `String` | |
| `Integer` | `Int` | |
| `Long` | `Long` | |
| `Boolean` | `Boolean` | |
| `Decimal` | `BigDecimal` | `java.math.BigDecimal` |
| `DateTime` | `OffsetDateTime` | `java.time.OffsetDateTime` |
| `UUID` | `UUID` | `java.util.UUID` |
| `optional<T>` | `T?` | Nullable type |
| `List<T>` | `List<T>` | Immutable by default |
| `Map<K,V>` | `Map<K,V>` | Immutable by default |

## Domain Errors & Handling
- **No Runtime Exceptions for Business Logic**: Domain Errors MUST be sealed classes or `Result<T>` types, NOT `RuntimeException`.
- **Sealed Error Hierarchy**: Define a base `sealed class DomainError` and module-specific subclasses (e.g., `data class PersonNotFound(val id: UUID) : DomainError()`).
- **Return Signatures**: Command/Query handlers should return `Result<T, DomainError>` or throw typed exceptions caught by `StatusPages`.
- **Inspection**: The Presentation layer (Ktor `StatusPages`) must use `when` on the `DomainError` type to map to HTTP status codes exhaustively.

## Coroutine Patterns for CQRS
- **Command Handlers**: `suspend fun handle(command: CreatePersonCommand): Result<UUID, DomainError>`
- **Query Handlers**: `suspend fun handle(query: GetPersonQuery): Result<PersonDto, DomainError>`
- **Transaction Scope**: Wrap DB transactions in `suspendTransaction { ... }` (Exposed) or `db.useConnection { ... }`.
- **Event Publishing**: Fire events in a `launch { }` block only AFTER the transaction commits to avoid phantom reads.
- **Subscriber Processing**: Use `runBlocking` or `Dispatchers.IO` for AMQP consumer callbacks that invoke `suspend` handlers.

## Idiomatic Project Structure
- **Source Root**: `src/main/kotlin/{base_package}/`
- **Test Root**: `src/test/kotlin/{base_package}/`
- **Resources Root**: `src/main/resources/`
- **Base Package**: The `{base_package}` string (e.g., `com.company.app`) MUST be defined in the implementation's `config` file via a `Base Package: ...` directive. The Builder must construct the directory tree to project modules inside this base package.
