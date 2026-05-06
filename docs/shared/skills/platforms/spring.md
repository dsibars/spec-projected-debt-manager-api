# Platform Skill: Spring Boot

## Standards
- **Version**: Spring Boot 3.2.x.
- **Dependency Injection**: Constructor-based injection is required. Avoid `@Autowired` on fields.

## Web Layer
- Use `@RestController` for API endpoints.
- Define Request/Response DTOs as `records`.
- Use `@Valid` for request body validation.
- Implement a `@ControllerAdvice` for global error handling, mapping Domain Errors to HTTP status codes.

## Persistence Layer
- Use Spring Data JPA.
- **Strictly Forbidden**: Do NOT use `spring.jpa.hibernate.ddl-auto=update`. Schema generation must be disabled.
- **Mandatory Migrations**: The implementation MUST integrate Flyway or Liquibase to execute the Pure SQL migrations mandated by the `@shared/skills/persistence/postgresql` skill.
- Repositories must be interfaces extending `JpaRepository`.
- Logic layers must use the Repository interfaces, not implementations.

## Configuration
- Use `application.yml` for configuration.
- Environment variables must override configuration for secrets and infrastructure addresses.
