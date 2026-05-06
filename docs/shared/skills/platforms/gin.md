# Platform Skill: Gin

## Standards
- **Version**: Gin v1.9.x.

## Architecture
- Use `gin.Engine` for routing.
- Group routes by version and module (e.g., `/api/v1/people`).
- Middleware for logging, recovery, and authentication.

## Handlers
- Handlers receive `*gin.Context`.
- Use `c.ShouldBindJSON` for request parsing.
- Use a helper function for consistent JSON error responses.

## Dependency Injection
- Use "Poor Man's DI" (Constructor functions) to wire up services and repositories.
