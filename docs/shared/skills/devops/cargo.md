# Skill: DevOps - Cargo (Rust)

This skill defines the standards for dependency management and build orchestration in Rust-based implementations.

## Standards
- **Dependency Management**: Use `cargo` (`Cargo.toml`, `Cargo.lock`).
- **Tooling**: Use `rustc` via `cargo`.
- **Edition**: Target the latest stable edition (e.g., `2021`).

## Makefile Integration
- `make build`: Must execute `cargo build`.
- `make test`: Must execute `cargo test`.
- `make run`: Must execute `cargo run`.

## Best Practices
- Use `clippy` for linting.
- Use `rustfmt` for formatting.
- Project modules into `src/modules/` to maintain the mirror rule.
