# Meta Skill: Compilation Algorithm

## Category: meta
## Provides:
- Compilation Algorithm
## Conflicts With:
- None
## Depends On:
- None


This skill provides the operational detail for the 4-Phase Compilation Algorithm defined in AGENTS.md Section 2.

## Purpose
Make the compilation process deterministic and auditable by defining exactly what happens in each phase, what artifacts are produced, and what constitutes a halting condition.

---

## Phase I: Parse & Validate

### Step 1.1: Module Discovery
Scan `docs/specs/` for directories containing `README.md` and `implementation/mapping.md`. Each is a valid module.

### Step 1.2: Reference Graph Construction
For every spec file:
- Extract all `[[...]]` references using regex: `\[\[(.*?)\]\]`
- Extract all `@shared/skills/...` references using regex: `@shared/skills/([a-zA-Z0-9/_-]+)`
- Build a directed graph: nodes are files, edges are references.

### Step 1.3: Reference Resolution
For every edge in the graph:
- Resolve `[[models/X]]` relative to the containing module.
- Resolve `[[specs/Y/models/Z]]` relative to `docs/specs/Y/models/Z.md`.
- Resolve `@shared/skills/A/B` to `docs/shared/skills/A/B.md`.
If resolution fails, emit LINT error (Rule R1/R2) and HALT.

### Step 1.4: Dependency Graph Export
Produce `.spd/dependency-graph.json` containing:
```json
{
  "modules": ["identity", "people", "debts"],
  "edges": [
    {"from": "debts/commands/CreateDebt.md", "to": "people/models/Person.md", "type": "model-ref"},
    {"from": "debts/presentation/subscribers/SyncPerson.md", "to": "people/events/PersonCreated.md", "type": "event-ref"}
  ]
}
```

### Halting Condition
If any reference is unresolvable, HALT before Phase II.

---

## Phase II: Contract Check

### Step 2.1: Skill Loading
For each module, load:
1. All skills listed in `specs/shared/implementation.md` (global baseline).
2. All skills listed in the module's `implementation/mapping.md` (module override).
Apply Skill Precedence Rules (AGENTS.md Section 6) to resolve conflicts.

### Step 2.2: Zero Technical Leakage Scan
For every file in `models/`, `projections/`, `commands/`, `queries/`:
- Load a forbidden-terms list from `@shared/skills/patterns/clean-code` or default to: `JSON`, `SQL`, `JWT`, `React`, `HTTP`, `DTO`, `Entity`.
- If any forbidden term appears outside of `presentation/` or `implementation/`, emit a warning.

### Step 2.3: Type Graph Validation
Ensure every model reference is type-compatible:
- A command referencing `[[models/Debt]]` must receive a `Debt` aggregate in its flow.
- A projection referencing `[[models/Person]]` is INVALID (projections must reference read models, not foreign aggregates). This is a common mistake.

### Halting Condition
If skill conflicts cannot be resolved by precedence rules, HALT.

---

## Phase III: Synthesize

### Step 3.1: Platform Skill Loading
Load the platform skill specified in `implementations/[target]/config` (e.g., `@shared/skills/platforms/spring`).

### Step 3.2: Structural Mapping Application
For each spec file, apply the platform's mapping function:
- `models/X.md` → `src/.../models/X.java` (or `.go`, `.rs`)
- `commands/Y.md` → `src/.../commands/YHandler.java` + potentially `YRequest.java`
- `behaviors/Z.md` → `src/test/.../ZTest.java`

### Step 3.3: Skill Synthesis
For each generated artifact, inject technical boilerplate:
- Persistence skill → Repository interfaces, entity annotations, migration SQL.
- Security skill → JWT extraction filters, context injection.
- Messaging skill → Outbox entity, event publishers, subscriber adapters.

### Step 3.4: Benchmark Harness Generation
The Builder MUST generate a benchmark harness conforming to `@shared/skills/testing/benchmarking`:
1. Generate the benchmark entrypoint (e.g., `cmd/benchmark/main.go`, `src/bin/benchmark.rs`).
2. Generate deterministic data generators using seed `42`.
3. Generate HTTP client calls for all 5 benchmark phases.
4. Generate the report writer that emits `benchmark-report.json`.

### Step 3.5: Assumption Recording
If a technical choice has no covering skill:
1. Check `tech_assumptions.md` for an existing entry.
2. If found, apply it.
3. If not found, generate a new assumption entry and apply the most conservative default.

### Halting Condition
None in this phase. Synthesis is interpretive and may produce imperfect output, which is caught in Phase IV.

---

## Phase IV: Verify

### Step 4.1: Test Generation
Transform `behaviors/*.md` Gherkin-style scenarios into executable tests using the target's testing framework.

### Step 4.2: Compilation
Run `make build`. If compilation fails, capture errors.

### Step 4.3: Test Execution
Run `make test`. Capture pass/fail counts.

### Step 4.4: Report Generation
Produce `.spd/build-report.yaml`:
```yaml
build_id: "uuid"
timestamp: "2026-05-21T15:24:56Z"
target: "java"
status: FAILURE
phase_reached: IV
compilation:
  status: OK
  errors: 0
tests:
  status: FAILURE
  passed: 12
  failed: 3
  failures:
    - test: "DebtManagementTest.settlingDebt"
      message: "expected 0 but was 100"
```

### Step 4.5: Builder Retry (if configured)
If `status == FAILURE` and `retry_count < max_retries`:
- Parse build/test errors.
- Attempt to fix implementation WITHOUT modifying specs.
- Increment `retry_count`.
- Return to Step 4.2.

### Halting Condition
If all retries exhausted, HALT. Task status becomes `BUILD_FAILURE`.

---

## Artifact Summary

| Phase | Output Artifact | Location |
|-------|----------------|----------|
| I | `dependency-graph.json` | `implementations/[target]/.spd/` |
| II | `contract-check.log` | `implementations/[target]/.spd/` |
| III | Source code | `implementations/[target]/src/` |
| III | Benchmark harness | `implementations/[target]/src/` (or `cmd/benchmark/`) |
| IV | `build-report.yaml` | `implementations/[target]/.spd/` |
| IV | `benchmark-report.json` | `implementations/[target]/` (gitignored, retained locally) |