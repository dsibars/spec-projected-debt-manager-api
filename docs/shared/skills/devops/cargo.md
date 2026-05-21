# Skill: DevOps - Cargo (Rust)

## Category: devops
## Depends On:
- None explicitly declared
## Provides:
- Cargo
## Conflicts With:
- configuration-management
- dockerization
- go-modules
- health-probes
- maven

This skill defines the standards for dependency management and build orchestration in Rust-based implementations.

## Standards
- **Dependency Management**: Use `cargo` (`Cargo.toml`, `Cargo.lock`).
- **Tooling**: Use `rustc` via `cargo`.
- **Edition**: Target the latest stable edition (e.g., `2021`).
- **Linting**: `cargo clippy -- -D warnings` is mandatory in CI.
- **Formatting**: `cargo fmt --check` is mandatory in CI.

## Project Structure

### Single-Crate Layout (Default)
For projects where domain + infra + presentation fit in one crate:

```
Cargo.toml          # Workspace root or single crate
src/
  lib.rs            # Domain + Application + Infrastructure modules
  bin/
    api.rs          # Axum HTTP server entrypoint
    worker.rs       # AMQP consumer entrypoint
```

The `Cargo.toml` MUST declare both binaries:
```toml
[[bin]]
name = "api"
path = "src/bin/api.rs"

[[bin]]
name = "worker"
path = "src/bin/worker.rs"
```

### Multi-Crate Workspace (Recommended for Large Projects)
```
Cargo.toml          # Workspace manifest
 crates/
   domain/
   application/
   infrastructure/
   api/              # Depends on infrastructure + application
   worker/           # Depends on infrastructure + application
```

The workspace `Cargo.toml`:
```toml
[workspace]
members = ["crates/domain", "crates/application", "crates/infrastructure", "crates/api", "crates/worker"]
```

## Makefile Integration
- `make build`: Must execute `cargo build --release` (builds both binaries).
- `make test`: Must execute `cargo test --workspace`.
- `make run`: Must execute `cargo run --bin api`.
- `make run-worker`: Must execute `cargo run --bin worker`.
- `make migrate`: Must execute `sqlx migrate run` (requires `sqlx-cli`).

## Best Practices
- Use `clippy` for linting.
- Use `rustfmt` for formatting.
- Project modules into `src/modules/` (or `crates/*/src/`) to maintain the mirror rule.
- Pin dependency versions in `Cargo.toml` using exact versions or caret ranges (`^`).
