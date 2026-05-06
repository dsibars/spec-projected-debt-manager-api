# Shared Implementation: Global Baselines

This document defines the base technical composition rules that apply to all modules in the system.

## Global Skill Assignments

Every module in the `docs/specs/` directory automatically inherits the following shared skills unless explicitly overridden in its own `implementation/` mapping:

1.  **Architecture**: `@shared/skills/patterns/simplified-ddd`
2.  **DevOps**: `@shared/skills/devops/dockerization`
3.  **Testing**: `@shared/skills/testing/strategy`

## Technical Composition Rules

- **Zero Technical Leakage**: Spec files in `logic/` and `models/` must remain purely declarative.
- **Mirror Rule**: The generated source code must mirror the spec hierarchy 1:1.
- **Makefile Integrity**: Each implementation must provide a Makefile that supports `infra-up`, `build`, `test`, and `run` as defined in the DevOps and Testing skills.
