# Skill: Simplified DDD Approach

This skill defines the architectural patterns for a "Simplified Domain Driven Design" (DDD).

## Core Principles

1.  **Ubiquitous Language**: Matching terms from `docs/specs/[module]/definitions`.
2.  **Hexagonal Structure**: `domain/`, `application/`, `presentation/`, `infrastructure/`.

3.  **Law of Optimistic Locking**:
    - **Aggregates**: MUST include a `version` (Integer).
    - **Projections (Inline)**: Any projection updated via the "Inline Update" pattern MUST also include a `version` field to prevent race conditions during atomic dual-writes.
    - Repositories MUST enforce `WHERE version = :oldVersion` and throw `ConcurrencyConflict` on failure.

4.  **Binary Structural Law**:
    - To support separate REST and Worker binaries, the project structure MUST decouple the "Runners" from the "Logic".
    - **ApiRunner**: Bootstraps the application with `presentation/rest/` adapters enabled.
    - **WorkerRunner**: Bootstraps the application with `presentation/subscribers/` adapters enabled.
    - Both runners share the same `application/` and `domain/` layers.

5.  **Multi-Tenancy Isolation Law**: 
    - Functionally: `tenantId` == `userId`.
    - Sharding: `sid` identifying the physical partition.

6.  **Persistence Segregation Law**:
    - `WRITE_DB` (Aggregates) vs `READ_DB` (Projections).
    - No physical coupling (FKs) between Write and Read sides.
