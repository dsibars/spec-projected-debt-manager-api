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

## Error Handling
- Use checked exceptions for expected domain failures (mapped to Domain Errors in specs).
- Use unchecked exceptions for programming errors or systemic failures.
