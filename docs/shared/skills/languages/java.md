# Language Skill: Java

## Category: languages
## Provides:
- Java
## Conflicts With:
- go
- kotlin
- rust
## Depends On:
- None explicitly declared


## Standards
- **Version**: Java 21 LTS.
- **Style Guide**: Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
- **LTS Compliance**: All language features MUST be compatible with Java 21.

## Best Practices
- **Records**: For DTOs, Value Objects, and Command/Query request objects.
- **Mutable Aggregates**: Standard classes with private fields, domain-rich methods, and no-args constructors for JPA if applicable.
- **Null Safety**: Use `Optional<T>` for nullable returns. Avoid `null` in public APIs.
- **Immutability**: Prefer `final` fields and immutable collections where possible.
- **Optimistic Locking**: Use `@Version` (if using JPA) or manual `version` increments in repositories.

## Type Mapping Law
The Builder MUST project specification types to Java types according to the following table:

| Spec Type | Java Type | Notes |
|---|---|---|
| `String` | `String` | |
| `Integer` | `Integer` | |
| `Long` | `Long` | 64-bit |
| `Boolean` | `Boolean` | |
| `Decimal` | `BigDecimal` | Use `MathContext.DECIMAL128` for precision |
| `DateTime` | `OffsetDateTime` | ISO-8601 with timezone |
| `UUID` | `UUID` | `java.util.UUID` |
| `optional<T>` | `Optional<T>` | |
| `List<T>` | `List<T>` | Use immutable list if not mutated |
| `Map<K,V>` | `Map<K,V>` | |

## Domain Errors & Handling
- **No Runtime Exceptions for Business Logic**: Domain Errors MUST be checked exceptions or dedicated result types, NOT `RuntimeException`.
- **Custom Exception Types**: Define a base `DomainException extends Exception` and module-specific subclasses (e.g., `PersonNotFoundException extends DomainException`).
- **Return Signatures**: Use Cases (Command/Query Handlers) should declare `throws DomainException` or return `Result<T, DomainError>` if using a functional result library.
- **Inspection**: The Presentation layer (`@ControllerAdvice`) must catch `DomainException` and map to the appropriate HTTP status code.

## Idiomatic Project Structure
- **Source Root**: `src/main/java/{base_package}/`
- **Test Root**: `src/test/java/{base_package}/`
- **Resources Root**: `src/main/resources/`
- **Base Package**: The `{base_package}` string (e.g., `com.company.app`) MUST be defined in the implementation's `config` file via a `Base Package: ...` directive. The Builder must construct the directory tree to project modules inside this base package.
