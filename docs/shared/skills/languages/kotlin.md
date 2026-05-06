# Language Skill: Kotlin

## Standards
- **Version**: Kotlin 1.9.22+.
- **Runtime**: JVM 21.
- **Style Guide**: Follow [Official Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html).

## Best Practices
- **Immutability**: Use `val` by default. Use `data class` for domain models and DTOs.
- **Null Safety**: Leverage Kotlin's type system to eliminate `NullPointerException`.
- **Coroutines**: Use structured concurrency for all asynchronous operations.
- **Functional Idioms**: Prefer `map`, `filter`, `fold` over imperative loops.
- **Extension Functions**: Use to add utility logic to classes without inheritance.

## Error Handling
- Use `Result<T>` or custom sealed classes for domain-level error representation.
- Reserved exceptions are only for systemic/unrecoverable failures.
