# Platform Skill: Gin

## Category: platforms
## Provides:
- Gin
## Conflicts With:
- axum
- ktor
- spring
## Depends On:
- None explicitly declared


## Standards
- **Version**: Gin v1.9.x.
- **HTTP Engine**: `net/http` (via Gin's wrapper).

## Architecture
- Use `gin.Engine` for routing. Group routes by version and module (e.g., `/api/v1/people`).
- Handlers receive `*gin.Context`.
- Middleware for logging (`gin.Logger()`), recovery (`gin.Recovery()`), CORS, and authentication.
- **Structured Logging**: Use `zerolog` or `zap` for structured JSON logging. Gin's default logger is insufficient for production.

## State Management
- Use a custom `AppState` struct and inject it via `c.Set("state", state)` in a middleware, or use a closure/factory pattern.
- The `AppState` should hold:
    - `PrimaryPool *pgxpool.Pool`.
    - `ReplicaPool *pgxpool.Pool` (optional).
    - `CommandBus CommandBus`.
    - `QueryBus QueryBus`.
    - `EventPublisher EventPublisher`.
- Handlers retrieve the state via type assertion from `gin.Context`.

## Validation
- Use `go-playground/validator/v10` for request body validation.
- Define request DTOs as structs with `validate:"required,email"` tags.
- Implement a centralized `BindAndValidate` helper that calls `c.ShouldBindJSON`, then `validate.Struct()`, and returns a standardized `ValidationError` response with `422` status.

## Authentication & Authorization
- Implement a Gin middleware function (`func AuthMiddleware() gin.HandlerFunc`) that:
    1. Extracts the `Authorization` header.
    2. Validates the JWT (signature, expiry, issuer).
    3. Injects the claims (userId, tenantId) into `gin.Context` via `c.Set("claims", claims)`.
    4. Rejects with `401` or `403` before reaching the handler.
- Protected routes MUST use `router.Group("/api", AuthMiddleware())`.

## Error Handling
- Implement a centralized `ErrorHandler` middleware or helper.
- Map Domain Errors (custom `error` types) to HTTP status codes:
    - `NotFoundError` → `404`
    - `ValidationError` → `422`
    - `ConcurrencyConflict` → `409`
    - `UnauthorizedError` → `401`
- All error responses MUST follow the same JSON envelope: `{"error": "CODE", "message": "...", "details": [...]}`.

## Transaction Management
- Commands MUST acquire a `pgx.Tx` from `pgxpool.Pool.Begin(ctx)`, pass it to repositories, and commit only after the outbox event is inserted.
- Use `defer tx.Rollback(ctx)` as a safety net; call `tx.Commit(ctx)` on success.

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Server Port**: `server.port` (Default: `8080`)
- **Mode**: `GIN_MODE` (e.g., `debug`, `release`)

## Default Tuning
- **Read Timeout**: `10s`.
- **Write Timeout**: `10s`.
- **Max Body Size**: `1MB`.

## Binary Split (Api vs Worker)
The Builder MUST produce two binaries:
- **`api`**: Gin HTTP server. Entrypoint: `cmd/api/main.go`. Boots the Gin engine and REST adapters only.
- **`worker`**: Background event processor. Entrypoint: `cmd/worker/main.go`. Boots the AMQP consumer and subscriber adapters only.
- Both binaries share `internal/` (domain + application layers). The `cmd/` directory contains only `main.go` files.
