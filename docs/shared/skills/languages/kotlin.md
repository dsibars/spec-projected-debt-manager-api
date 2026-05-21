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

## Best Practices
- **Immutability**: Use `val` by default. Use `data class` for domain models and DTOs.
- **Null Safety**: Leverage Kotlin's type system to eliminate `NullPointerException`.
- **Coroutines**: Use structured concurrency for all asynchronous operations.
- **Type Mapping Law**: Any property marked as optional (e.g., `email?`) or nullable in the specifications MUST be projected as a nullable type `T?` in Kotlin to leverage the language's native null-safety features.
- **Functional Idioms**: Prefer `map`, `filter`, `fold` over imperative loops.
- **Extension Functions**: Use to add utility logic to classes without inheritance.

## Error Handling
- Use `Result<T>` or custom sealed classes for domain-level error representation.
- Reserved exceptions are only for systemic/unrecoverable failures.

## Idiomatic Project Structure
- **Source Root**: `src/main/kotlin/{base_package}/`
- **Test Root**: `src/test/kotlin/{base_package}/`
- **Resources Root**: `src/main/resources/`
- **Base Package**: The `{base_package}` string (e.g., `com.company.app`) MUST be defined in the implementation's `config` file via a `Base Package: ...` directive. The Builder must construct the directory tree to project modules inside this base package.