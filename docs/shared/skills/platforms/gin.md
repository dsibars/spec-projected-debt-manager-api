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

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Server Port**: `GIN_PORT` (Default: `8080`)
- **Mode**: `GIN_MODE` (e.g., `debug`, `release`)

## Default Tuning
- **Read Timeout**: `10s`.
- **Write Timeout**: `10s`.
- **Max Body Size**: `1MB`.
