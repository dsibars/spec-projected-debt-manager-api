# Platform Skill: Axum

## Category: platforms
## Provides:
- Axum
## Conflicts With:
- gin
- ktor
- spring
## Depends On:
- None explicitly declared


## Standards
- **Version**: Axum 0.7.x.
- **Runtime**: Tokio 1.35+ (multi-thread scheduler).
- **HTTP Engine**: Hyper (via Axum's default).

## Architecture
- Use `axum::Router` for defining API endpoints. Group routes by version and module (e.g., `/api/v1/people`).
- Handlers should be async functions receiving `axum::extract::State<AppState>` and `axum::extract::Json<T>`.
- Use `tower-http` for middleware (CORS, Trace, Compression, Timeout).
- **Structured Logging**: Use `tracing` + `tracing-subscriber` with JSON formatting in production.

## State Management
- Use `axum::extract::State` to share database pools and shared services.
- Shared state must be thread-safe (`Arc<T>` or cloneable by design).
- The `AppState` struct should hold:
    - `primary_pool: Pool<Postgres>` (deadpool or sqlx).
    - `replica_pool: Option<Pool<Postgres>>`.
    - `command_bus: Arc<dyn CommandBus>`.
    - `query_bus: Arc<dyn QueryBus>`.
    - `event_publisher: Arc<dyn EventPublisher>`.

## Validation
- Use the `validator` crate with `serde` for request body validation.
- Define request DTOs as structs with `#[derive(Deserialize, Validate)]`.
- Implement a custom `IntoResponse` for `validator::ValidationErrors` to return `422 Unprocessable Entity` with structured error details.

## Authentication & Authorization
- Use `tower-http::auth::RequireAuthorizationLayer` OR a custom `axum::middleware::from_fn` extractor.
- JWT validation MUST happen in a `from_fn` middleware that extracts the `Authorization` header, validates the token, and injects the claims into `Request` extensions.
- The middleware MUST reject requests with `401 Unauthorized` or `403 Forbidden` before the handler is reached.

## Error Handling
- Implement a single `ApiError` enum deriving `thiserror` and `IntoResponse`.
- Map Domain Errors to HTTP status codes in a centralized `IntoResponse` implementation.
- Use `axum::response::(StatusCode, Json<ErrorResponse>)` for consistent JSON error bodies.

## Transaction Management
- Commands MUST acquire a `sqlx::Transaction` from the pool, pass it to the repository, and commit only after the outbox event is inserted.
- Use `sqlx::Transaction<'_, Postgres>` as the transaction handle type.

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Server Address**: `server.port` → bound to `0.0.0.0:{port}` (Default: `8080`)
- **Worker Threads**: `TOKIO_WORKER_THREADS` (Default: CPU count)

## Default Tuning
- **Timeout**: Use `tower_http::timeout::TimeoutLayer` (Default: `30s`).
- **Body Limit**: Use `axum::extract::DefaultBodyLimit` (Default: `1MB`).
- **Request ID**: Inject `x-request-id` via `tower_http::request_id` for trace correlation.

## Binary Split (Api vs Worker)
The Builder MUST produce two binaries:
- **`api`**: Axum HTTP server. Built via `cargo build --bin api`. Entrypoint: `src/bin/api.rs`. Boots the router and REST adapters only.
- **`worker`**: Background event processor. Built via `cargo build --bin worker`. Entrypoint: `src/bin/worker.rs`. Boots the AMQP consumer and subscriber adapters only.
- Both binaries share `src/lib.rs` (domain + application layers). Use Cargo workspace members if the project grows beyond a single crate.
