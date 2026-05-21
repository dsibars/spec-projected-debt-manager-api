# Language Skill: Rust

## Category: languages
## Provides:
- Rust
## Conflicts With:
- go
- java
- kotlin
## Depends On:
- None explicitly declared


## Standards
- **Version**: Rust 1.76+ (Stable).
- **Edition**: 2021.
- **Style Guide**: Follow `rustfmt` defaults. Run `cargo fmt --check` in CI.
- **Linting**: `cargo clippy -- -D warnings` is MANDATORY in CI.

## Best Practices
- **Ownership**: Strict adherence to borrowing rules. Avoid `Clone` unless necessary. Use `Arc<T>` for shared ownership across async boundaries.
- **Type Safety**: Use Newtype pattern for domain identifiers (e.g., `struct PersonId(Uuid)`).
- **Enums**: Use algebraic data types (Enums) for state management and errors.
- **Async**: Use `tokio` as the standard runtime. Prefer `tokio::spawn` for fire-and-forget tasks (e.g., outbox polling).
- **Serde**: Use `serde` for all serialization/deserialization needs. Enable `derive` feature.
- **Type Mapping Law**: Any property marked as optional (e.g., `email?`) or nullable in the specifications MUST be projected as an `Option<T>` in the Rust implementation to leverage the language's safety and functional pattern matching.

## Type Mapping Law

| Spec Type | Rust Type | Notes |
|---|---|---|
| `String` | `String` | |
| `Integer` | `i32` | |
| `Long` | `i64` | |
| `Boolean` | `bool` | |
| `Decimal` | `rust_decimal::Decimal` | Use `rust_decimal` crate |
| `DateTime` | `chrono::DateTime<chrono::Utc>` | ISO-8601 UTC |
| `UUID` | `uuid::Uuid` | Use `uuid` crate with `v4` feature |
| `optional<T>` | `Option<T>` | |
| `List<T>` | `Vec<T>` | |
| `Map<K,V>` | `std::collections::HashMap<K,V>` | |

## Domain Errors & Handling
- **No Exceptions**: Rust does not have exceptions. The Builder must NEVER translate DDD "Exceptions" into `panic!` calls.
- **Algebraic Data Types**: Explicit Domain Errors defined in the specs must be synthesized as a module-specific `Enum` (e.g., `pub enum PersonDomainError { NotFound(Uuid), InvalidEmail }`).
- **thiserror**: Use the `thiserror` crate to implement the `std::error::Error` trait for these Domain Error enums automatically.
- **Return Signatures**: Use Cases must return `Result<T, DomainError>`.
- **Inspection**: The Presentation layer must use `match` statements on the `DomainError` enum to map it to the appropriate HTTP status code.

## Idiomatic Project Structure
- **Source Root**: `src/`
- **Library Root**: `src/lib.rs` (shared domain + application code).
- **Binary Entries**: `src/bin/api.rs` and `src/bin/worker.rs`.
- **Module Hierarchy**: The Builder must map each projected module to a Rust module declared explicitly via `pub mod [module];` inside `src/lib.rs`.
- **Workspace**: For large projects, use a Cargo workspace with separate crates for `domain`, `application`, `infrastructure`, `api`, and `worker`.

## Recommended Crate Ecosystem
| Concern | Crate |
|---|---|
| Web Framework | `axum` |
| Async Runtime | `tokio` |
| Serialization | `serde`, `serde_json` |
| Validation | `validator` |
| DB Pool | `deadpool-postgres`, `sqlx` |
| Migrations | `sqlx-cli` |
| AMQP Client | `lapin` |
| Redis | `redis` |
| JWT | `jsonwebtoken` |
| Logging | `tracing`, `tracing-subscriber` |
| Error Derive | `thiserror` |
| Testing | `tokio-test`, `reqwest` |
| Configuration | `config` |
