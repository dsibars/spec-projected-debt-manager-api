# Meta Skill: Spec Validation (LINT)

## Category: meta
## Provides:
- Spec Validation
## Conflicts With:
- None
## Depends On:
- None


This skill defines the formal validation rules for the `LINT` directive. It operates exclusively on the Formal Layer (Section 1 of AGENTS.md).

## Purpose
Detect structural errors, broken references, and contradictions in specs BEFORE any code generation. A spec that fails LINT MUST NOT be frozen or built.

## Input
- `docs/specs/` directory tree.
- `docs/shared/skills/` directory tree.

## Output
- `lint-report.yaml` (or stdout in CI) containing errors, warnings, and module health.

---

## Validation Rules

### R1: Reference Resolution
Every `[[...]]` cross-reference MUST resolve to an existing file path.
- `[[models/User]]` in `docs/specs/identity/commands/RegisterUser.md` MUST resolve to `docs/specs/identity/models/User.md`.
- `[[specs/people/models/Person]]` MUST resolve to `docs/specs/people/models/Person.md`.
- Broken references are **errors**.

### R2: Skill Reference Validity
Every `@shared/skills/...` reference MUST resolve to an existing skill file.
- `@shared/skills/persistence/postgresql` MUST resolve to `docs/shared/skills/persistence/postgresql.md`.
- Missing skills are **errors**.

### R3: Module Completeness
Every module MUST contain:
- `README.md`
- `implementation/mapping.md`
- `definitions/` with at least a glossary file
Missing required files are **errors**.

### R4: Event Declaration Consistency
- Every event emitted in a `commands/*.md` `## Emits` section MUST have a corresponding file in the module's `events/` directory.
- Every event consumed in a `presentation/subscribers/*.md` MUST be declared in the module's `README.md` under "Events Consumed".
- Missing event definitions are **errors**.

### R5: Model/Schema Consistency
- Every property listed in a `models/*.md` file MUST appear in the module's `implementation/mapping.md` OR be explicitly marked with `@derived` or `@transient`.
- Every column in `implementation/mapping.md` MUST correspond to a property in a `models/*.md` or `projections/*.md` file.
- Properties without mapping or annotation are **warnings**.
- Extra columns without spec origin are **warnings**.

### R6: Return Type Consistency
- Commands MUST return `void` or at most an identifier of the created resource (e.g., `userId`, `debtId`). This aligns with `@shared/skills/patterns/cqrs-and-events`.
- Commands MUST NOT return full Domain Entities or DTOs.
- If a command spec declares a `## Result` section, it MUST contain only an identifier or `void`.
- Queries MUST return the type declared in their spec.

### R7: Circular Dependency Detection
- Module A MUST NOT transitively depend on itself through cross-module references.
- Circular dependencies are **errors**.

### R8: Claim Uniqueness
- Domain error names MUST be unique within a module.
- Event type names MUST be unique across the entire system.
- Duplicate claims are **errors**.

### R9: JWT Claim Consistency
- All `behaviors/` and `presentation/` specs MUST use the same JWT claim names defined in `@shared/skills/security/jwt`.
- Claim name mismatches (e.g., `tid` vs `sub`) are **errors**.

---

## Report Format

```yaml
lint_version: "1.0"
timestamp: "2026-05-21T15:24:56Z"
status: FAIL  # PASS, FAIL, or WARN
summary:
  errors: 3
  warnings: 7
modules:
  identity:
    status: PASS
  debts:
    status: FAIL
    issues:
      - rule: R5
        severity: error
        file: "docs/specs/debts/models/Debt.md"
        message: "Property 'direction' not found in implementation/mapping.md"
      - rule: R9
        severity: error
        file: "docs/specs/identity/behaviors/auth.md"
        message: "JWT claim 'tid' does not match skill definition 'sub'"
```