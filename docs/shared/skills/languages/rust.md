# Language Skill: Rust

## Standards
- **Version**: Rust 1.76+ (Stable).
- **Edition**: 2021.
- **Style Guide**: Follow `rustfmt` defaults.

## Best Practices
- **Ownership**: Strict adherence to borrowing rules. Avoid `Clone` unless necessary.
- **Type Safety**: Use Newtype pattern for domain identifiers (e.g., `struct PersonId(Uuid)`).
- **Enums**: Use algebraic data types (Enums) for state management and errors.
- **Async**: Use `tokio` as the standard runtime.
- **Serde**: Use `serde` for all serialization/deserialization needs.
- **Type Mapping Law**: Any property marked as optional (e.g., `email?`) or nullable in the specifications MUST be projected as an `Option<T>` in the Rust implementation to leverage the language's safety and functional pattern matching.

## Domain Errors & Handling
- **No Exceptions**: Rust does not have exceptions. The Builder must NEVER translate DDD "Exceptions" into `panic!` calls.
- **Algebraic Data Types**: Explicit Domain Errors defined in the specs must be synthesized as a module-specific `Enum` (e.g., `pub enum PersonDomainError { NotFound(Uuid), InvalidEmail }`).
- **thiserror**: Use the `thiserror` crate to implement the `std::error::Error` trait for these Domain Error enums automatically.
- **Return Signatures**: Use Cases must return `Result<T, DomainError>`.
- **Inspection**: The Presentation layer must use `match` statements on the `DomainError` enum to map it to the appropriate HTTP status code.

## Idiomatic Project Structure
- **Source Root**: `src/`
- **Module Hierarchy**: The Builder must map each projected module to a Rust module declared explicitly via `pub mod [module];` inside `src/lib.rs` or `src/main.rs`.
