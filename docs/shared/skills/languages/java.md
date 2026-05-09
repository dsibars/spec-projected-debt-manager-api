# Language Skill: Java

## Best Practices
- **Records**: For DTOs and Value Objects.
- **Mutable Aggregates**: Standard classes with private fields and domain-rich methods.
- **Optimistic Locking**: Use `@Version` (if using JPA) or manual `version` increments in repositories.

## Multi-Binary Build Law (Maven)
To support the "Binary Structural Law", the Builder MUST configure the `pom.xml` with specialized profiles or execution blocks:

1. **Profile `api`**: Sets the main class to `ApiRunner`.
2. **Profile `worker`**: Sets the main class to `WorkerRunner`.

Generating these separate artifacts allows the DevOps layer to deploy them with different resource limits and scaling policies.

## Type Mapping
Optional props $\rightarrow$ `Optional<T>`.
Long $\rightarrow$ `Long` (64-bit).
Decimal $\rightarrow$ `BigDecimal`.
DateTime $\rightarrow$ `OffsetDateTime`.
