# Platform Skill: Ktor

## Category: platforms
## Provides:
- Ktor
## Conflicts With:
- axum
- gin
- spring
## Depends On:
- None explicitly declared


## Standards
- **Version**: Ktor 2.3.x.
- **Engine**: Netty (default) or CIO.
- **Runtime**: JVM 21.

## Core Modules (Plugins)
- **Routing**: Explicit route definitions in separate files/modules. Group by version (e.g., `/api/v1/people`).
- **ContentNegotiation**: Use `kotlinx.serialization` for JSON. Register `JSON` content type.
- **StatusPages**: Centralized error handling to map Domain Errors to HTTP responses.
- **CallLogging**: For audit and debugging.
- **Authentication**: Use `Authentication` plugin with JWT validator.

## Architecture
- Use **Koin** for Dependency Injection. Define modules per layer (`domainModule`, `applicationModule`, `infrastructureModule`).
- Handlers should be lightweight, delegating business logic to Application Services (Command/Query Handlers).
- **Structured Logging**: Use `kotlin-logging` (mu.KLogger) with Logback JSON encoder in production.

## State Management
- The Ktor `Application` environment holds the Koin module graph.
- Repositories, Buses, and Pools are injected into route handlers via Koin's `by inject()` or constructor injection in Application Services.

## Validation
- Use `konform` or `valiktor` for request body validation.
- Define request DTOs as `data class` with validation rules applied in the handler or an interceptor.
- Return `422 Unprocessable Entity` for validation failures with a structured error list.

## Authentication & Authorization
- Configure the `Authentication` plugin with JWT:
    ```kotlin
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "spd"
            verifier(jwkProvider, issuer)
            validate { credential ->
                if (credential.payload.getClaim("sub").asString() != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
    ```
- Protect routes with `authenticate("auth-jwt") { ... }`.
- Extract `sub` (userId) from `call.principal<JWTPrincipal>()` and pass it to the application layer.

## Error Handling
- Use `StatusPages` plugin to intercept exceptions:
    ```kotlin
    install(StatusPages) {
        exception<DomainError> { call, cause ->
            call.respond(HttpStatusCode.fromDomainError(cause), cause.toResponse())
        }
        exception<ValidationException> { call, cause ->
            call.respond(HttpStatusCode.UnprocessableEntity, cause.errors)
        }
    }
    ```
- Domain Errors should be a sealed class hierarchy for exhaustive `when` matching.

## Transaction Management
- Use **Exposed** DSL or raw JDBC for transactions.
- Commands MUST wrap aggregate persistence and outbox insertion in a single transaction:
    ```kotlin
    transaction {
        // persist aggregate
        // insert outbox event
    }
    ```
- If using Exposed, ensure the transaction block commits only on success.

## Persistence Integration
- **Migration Tool**: Flyway Ktor plugin or manual Flyway execution on startup.
- **Connection Pool**: HikariCP configured in Koin module.
- **Query Builder**: Exposed DSL or jOOQ. Raw SQL is acceptable for complex projections.

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Server Port**: `server.port` (Default: `8080`)
- **Application Name**: `ktor.application.name`

## Default Tuning
- **Connection Timeout**: `15s`.
- **Request Timeout**: `30s`.
- **Max Request Size**: `1MB`.

## Binary Split (Api vs Worker)
The Builder MUST produce two executables:
- **`api`**: Ktor HTTP server. Entrypoint: `Application.kt` with `engineMain`. Boots routing and REST adapters only.
- **`worker`**: Background event processor. Entrypoint: `WorkerApplication.kt`. Boots the AMQP consumer and subscriber adapters only.
- Both share `src/main/kotlin/{package}/` domain and application layers. Use Gradle/Maven multi-module or profile-based main class selection.
