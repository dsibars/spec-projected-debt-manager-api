# Platform Skill: Spring Boot

## Category: platforms
## Provides:
- Spring
## Conflicts With:
- axum
- gin
- ktor
## Depends On:
- None explicitly declared


## Standards
- **Version**: Spring Boot 3.2.x.
- **Runtime**: JVM 21.
- **Dependency Injection**: Constructor-based injection is required. Avoid `@Autowired` on fields.

## Web Layer
- Use `@RestController` for API endpoints.
- Define Request/Response DTOs as Java `record`s or Kotlin `data class`es.
- Use `@Valid` for request body validation (Jakarta Validation / Hibernate Validator).
- Implement a `@ControllerAdvice` for global error handling, mapping Domain Errors to HTTP status codes.
- **Structured Logging**: Use Logback with `logstash-logback-encoder` for JSON structured logs in production.

## Persistence Layer
- Use Spring Data JPA for simple CRUD, or jOOQ / raw JDBC for complex queries.
- **Strictly Forbidden**: Do NOT use `spring.jpa.hibernate.ddl-auto=update`. Schema generation must be disabled.
- **Mandatory Migrations**: The implementation MUST integrate Flyway or Liquibase to execute the Pure SQL migrations mandated by the `@shared/skills/persistence/postgresql` skill.
- Repositories must be interfaces extending `JpaRepository` OR custom interfaces with `@Repository` implementations.
- Logic layers must use the Repository interfaces, not implementations.

## Authentication & Authorization
- Use Spring Security with JWT:
    - `SecurityFilterChain` configuring stateless session management.
    - Custom `JwtAuthenticationFilter` extending `OncePerRequestFilter` to validate tokens and populate `SecurityContextHolder`.
    - Extract `sub` claim as the authenticated user principal.
- Protect endpoints via `@PreAuthorize` or `requestMatchers().authenticated()`.

## Transaction Management
- Use `@Transactional` on Command Handlers and Event Subscriber methods.
- Ensure the transaction wraps: aggregate persistence, outbox event insertion, and inbox checkpoint writes.
- For cross-service transactions (eventual consistency), NEVER use distributed transactions. Rely on the Outbox Pattern.

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Server Port**: `server.port` (Default: `8080`)
- **App Name**: `spring.application.name` (Injected by Builder)
- **Profile**: `spring.profiles.active` (Injected by Builder: `local`, `prod`)

## Default Tuning (Production-Ready)
- **Log Format**: `logging.pattern.console` should be structured JSON in `prod`.
- **Performance**: `server.tomcat.max-threads` (Default: `200`).
- **JPA Tuning**: `spring.jpa.properties.hibernate.jdbc.batch_size` (Default: `50`).
- **Shutdown**: `server.shutdown: graceful` (Mandatory).

## Binary Split (Api vs Worker)
The Builder MUST produce two Spring Boot JARs from the same codebase:
- **`api`**: Spring Boot web application. Main class: `ApiRunner.java` / `ApiRunner.kt`. Includes `presentation/rest/` and web infrastructure only.
- **`worker`**: Spring Boot non-web application. Main class: `WorkerRunner.java` / `WorkerRunner.kt`. Disables the web server (`spring.main.web-application-type=none`) and boots AMQP consumers and subscriber adapters only.
- Configure Maven profiles or Gradle tasks to set the `start-class` manifest attribute for each artifact.
- Both share `domain/` and `application/` layers. The `presentation/` layer is split: `rest/` for API, `subscribers/` for Worker.
