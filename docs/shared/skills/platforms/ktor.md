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
- **Engine**: Netty (default).

## Core Modules (Plugins)
- **Routing**: Explicit route definitions in separate files/modules.
- **ContentNegotiation**: Use `kotlinx.serialization` for JSON.
- **StatusPages**: Centralized error handling to map Domain Errors to HTTP responses.
- **CallLogging**: For audit and debugging.

## Architecture
- Use Koin or Kodein for Dependency Injection.
- Handlers should be lightweight, delegating business logic to Application Services (Logic Layer).

## Deployment
- Project should be packaged as a fat JAR or native image using GraalVM.