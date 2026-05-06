# Language Skill: Java

## Standards
- **Version**: Java 21 LTS.
- **Style Guide**: Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

## Best Practices
- **Records**: Use `record` for all immutable data carriers (models, DTOs).
- **Streams**: Use `Stream API` for collection manipulation.
- **Optional**: Use `Optional<T>` for return types that may be empty. Avoid using `Optional` as a field or parameter.
- **Virtual Threads**: Utilize Project Loom (Virtual Threads) for high-throughput concurrency.
- **Lombok**: Avoid Lombok if possible, favoring native Java features (Records).
- **Type Mapping Law**: Any property marked as optional (e.g., `email?`) or nullable in the specifications MUST be projected as an `Optional<T>` in the Domain and Application layers to ensure explicit null-safety handling.

## Error Handling
- Use checked exceptions for expected domain failures (mapped to Domain Errors in specs).
- Use unchecked exceptions for programming errors or systemic failures.

## Idiomatic Project Structure
- **Source Root**: `src/main/java/{base_package}/`
- **Test Root**: `src/test/java/{base_package}/`
- **Resources Root**: `src/main/resources/`
- **Base Package**: The `{base_package}` string (e.g., `com.company.app`) MUST be defined in the implementation's `config` file via a `Base Package: ...` directive. The Builder must construct the directory tree to project modules inside this base package.
