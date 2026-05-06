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

## Error Handling
- Use `Result<T, E>` for all fallible operations.
- Implement the `std::error::Error` trait for custom error types.
- Use `thiserror` for library errors and `anyhow` for application-level context if needed.
