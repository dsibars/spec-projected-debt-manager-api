# Meta Skill: Review Protocol

This skill defines the exact validation procedures for the Reviewer agent (Section 9.3 of AGENTS.md).

## Purpose
Ensure every implementation projection is a faithful, deterministic synthesis of the specs and skills. The Reviewer is a read-only falsification engine.

## Input
- The spec file(s) referenced by the task under review.
- The generated source file(s) produced by the Builder.
- The assigned Shared Skills for the module.
- The module's `implementation/mapping.md`.

## Output
A review report in `.spd/reviews/` containing:
- Task ID and spec references.
- Pass/Fail verdict.
- A numbered list of violations, if any.
- Specific file paths and line numbers where possible.

---

## Validation Checklist

### 1. Structural Correspondence
- [ ] Every spec file in the task's scope has at least one corresponding artifact in `src/`.
- [ ] The directory structure follows the platform skill's mapping rules.
- [ ] No orphaned source files exist that do not map to a spec (unless explicitly marked as infrastructure by the platform skill).

### 2. Model Fidelity
- [ ] Every property declared in a `models/` spec file has a corresponding field in the generated model class/struct.
- [ ] Property types are consistent with `@shared/skills/meta/type-mapping`.
- [ ] Constraints from the spec (e.g., `totalAmount > 0`) are enforced in the generated code via validation, type limits, or constructor guards.
- [ ] Derived/computed properties in `projections/` are correctly implemented as computed, not persisted as independent mutable fields, unless the spec explicitly marks them as stored.

### 3. Command & Query Fidelity
- [ ] The generated command handler accepts all `## Input` parameters declared in the spec.
- [ ] Every `## Emits` event is published by the handler.
- [ ] Every `## Errors` domain error can be raised by the handler.
- [ ] Query handlers are side-effect-free (no writes, no event emissions).

### 4. Zero Technical Leakage
- [ ] Files in `domain/`, `models/`, `commands/` (application layer) do NOT import framework-specific packages (e.g., `javax.persistence`, `springframework`, `gin`, `axum`).
- [ ] Infrastructure concerns (HTTP, SQL, JSON serialization) exist ONLY in `presentation/` and `infrastructure/` layers.

### 5. Cross-Module Isolation
- [ ] No direct imports from another module's `domain/` or `models/` packages.
- [ ] Cross-module data is accessed ONLY via local Read Models updated by event subscribers.
- [ ] Every consumed event is declared in the module's `README.md` under "Events Consumed".

### 6. Event Contract Compliance
- [ ] Every emitted event payload matches the schema defined in the module's `events/*.md` file.
- [ ] Events include the standard `EventEnvelope` wrapper as defined in `specs/shared/events.md`.
- [ ] Event names follow the `[ModuleName].[PastTenseVerb]` convention.

### 7. Security Context Injection
- [ ] Every REST controller extracts `sub` (userId) and `sid` (shardId) from the JWT.
- [ ] Every Command and Query receives these as a mandatory Context argument.
- [ ] No handler performs manual token parsing.

### 8. Persistence Mapping Accuracy
- [ ] Every table/collection defined in `implementation/mapping.md` exists in the generated migrations.
- [ ] Column names and types match the mapping spec.
- [ ] Indexes declared in the mapping exist.
- [ ] No foreign keys span modules (enforced by schema review).

### 9. Test Coverage
- [ ] Every scenario in `behaviors/` has a corresponding automated test.
- [ ] Tests use the target language's standard framework.
- [ ] Tests compile and execute without failure.

---

## Verdict Rules

- **APPROVED**: Zero violations, all checklist items pass.
- **REJECTED**: One or more violations. The review MUST specify:
  - Severity: `CRITICAL` (blocks functionality) or `WARNING` (style/deviation).
  - Required action for the Builder to address.
- **NEEDS_CLARIFICATION**: The Reviewer cannot determine pass/fail because the spec is ambiguous. The task is blocked pending Architect intervention.
