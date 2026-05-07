# Platform Skill: Axum

## Standards
- **Version**: Axum 0.7.x.
- **Runtime**: Tokio.

## Architecture
- Use `Router` for defining API endpoints.
- Handlers should be async functions receiving `State` and `Json<T>`.
- Use `tower-http` for middleware (CORS, Trace, Compression).

## State Management
- Use `axum::extract::State` to share database pools and shared services.
- Shared state must be thread-safe (`Arc<T>`).

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Server Address**: `AXUM_ADDR` (Default: `0.0.0.0:8080`)
- **Worker Threads**: `TOKIO_WORKER_THREADS` (Default: CPU count)

## Default Tuning
- **Timeout**: Use `tower_http::timeout::TimeoutLayer` (Default: `30s`).
- **Body Limit**: Use `axum::extract::DefaultBodyLimit` (Default: `1MB`).
