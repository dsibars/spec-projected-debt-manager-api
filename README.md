# Spec Projected Debt Manager - API

This repository contains the declarative specifications and multiple technical implementations for the **Projected Debt Manager API**, built using the **Spec Projected Development (SPD)** methodology.

## 🚀 The SPD Approach

Following the principles defined in [AGENTS.md](./AGENTS.md), this project separates the **Truth Layer** (Documentation/Specs) from the **Projection Layer** (Implementation Code).

- **Specs as Source**: All business logic, models, and behaviors are defined in natural language within the `/docs` folder.
- **Idempotent Projections**: The source code in `/implementations/*/src` is a volatile, generated projection of the specs.
- **Multi-Implementation**: This repository demonstrates the power of SPD by maintaining identical business logic across four different tech stacks.

## 🛠 Target Implementations

This project maintains four distinct implementations:

1.  **Kotlin**: Modern JVM implementation.
2.  **Java**: Standard JVM implementation.
3.  **Rust**: High-performance, memory-safe implementation.
4.  **Go**: Simple, concurrent implementation.

## 📂 Project Structure

- `/docs`: The Truth Layer.
    - `/shared/skills`: Technical "laws" and integration patterns (e.g., PostgreSQL, DDD).
    - `/specs`: Business domain specifications.
- `/implementations`: The Projection Layer.
    - `/[target]/config`: Tech stack definitions.
    - `/[target]/src`: Generated source code (DO NOT EDIT MANUALLY).
    - `/[target]/Makefile`: Standard commands for build, test, and run.

## 🛠 Getting Started

To explore or run a specific implementation, navigate to its directory in `/implementations` and use the provided `Makefile`.

```bash
cd implementations/rust
make run
```

---

*This project is a sample of Spec Projected Development (SPD).*
