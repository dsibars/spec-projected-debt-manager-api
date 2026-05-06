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
- Use Spring Data JPA or Spring Data JDBC.
- Repositories must be interfaces extending `Repository`.
- Logic layers must use the Repository interfaces, not implementations.

## Configuration
- Use `application.yml` for configuration.
- Environment variables must override configuration for secrets and infrastructure addresses.
