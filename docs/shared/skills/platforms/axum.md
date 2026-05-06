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

## Serialization
- Use `serde` with `derive` for all DTOs.
- Custom error types should implement `IntoResponse` for easy mapping to HTTP errors.
