# The Declarative Architecture Manifest (v3.1 — Formal)

## Mission Statement
You are not a traditional coder. You are a **Cognitive Compiler**.
- Your **source code** is written in Natural Language located in `/docs/specs/`.
- Your **target output** is the Source Code located in `/implementations/[target]/src/`.
- The `src` directory is a volatile, idempotent projection of the `docs` directory.

SPD (Spec-Projected Development) operates in two layers:
1. **The Formal Layer** — Topology, references, skill assignments, and module schemas. This layer is mechanically validatable.
2. **The Interpretive Layer** — Natural language flow descriptions, business rules, and behavioral scenarios. This layer requires LLM synthesis.

> [!IMPORTANT]
> **CORE RULE:** Manual edits to any `src` folder are strictly prohibited and will be overwritten. To change the software, you **MUST** iterate the specs or the skills.

---

## 1. Repository Topology

### 1.1. The Truth Layer (`/docs`)
This layer is purely declarative and agnostic to the final implementation language.

- **`/shared/skills/`**: The "Physics" of the system. Tech-stack laws without business logic. Skills are human-intended tech stacks or integrations that humans control, define, and scope.
    - *Structure:* `[category]/[technology]` (e.g., `persistence/postgresql`, `ui/tailwind`).
    - Skills are operational: they MUST contain concrete rules, schemas, or code templates that a Builder can apply deterministically.
- **`/specs/shared/`**: Global specifications that apply across all modules.
    - `presentation.md`: The base UI/UX guidelines, design tokens, and layout principles. Module-specific presentation specs inherit and can override these.
    - `implementation.md`: The base technical composition rules. Module-specific implementation specs inherit these shared skill assignments.
    - `events.md`: Global event envelope standards and versioning rules.
- **`/specs/[module]/`**: Atomic Business Units. Follows DDD (Domain Driven Design).
    - `definitions/`: The Ubiquitous Language. Glossary and Domain Errors.
    - `models/`: Domain Aggregates (The Write Model).
    - `projections/`: Read Models and denormalized views (The UI Model).
    - `commands/`: State-changing business logic. Each file defines one command/use case.
    - `queries/`: Side-effect-free data retrieval. Each file defines one query.
    - `presentation/`: Driving Adapters.
        - `rest/`: REST API definitions (adapts HTTP to commands/queries).
        - `subscribers/`: Event subscribers (adapts Broker messages to commands).
    - `behaviors/`: Acceptance criteria (Given/When/Then).
    - `migrations/`: Declarative schema evolutions.
    - `implementation/`: Composition files mapping Module to Shared Skills.
    - `events/`: Domain events produced by this module.

> **Module Validity:** A valid module MUST contain at minimum:
> - A `README.md` declaring its bounded context, dependencies, and produced/consumed events.
> - An `implementation/mapping.md` file.
> - A `definitions/` folder with at least a glossary.
> - A module may have zero `commands/` or `queries/` only if it is a pure read-model or subscriber module (declared explicitly in README).

### 1.2. The Projection Layer (`/implementations`)
This layer contains the actual executable projects. A single repo can have multiple implementations. Each implementation is an isolated, idempotent projection of the specifications. All implementation-specific files and directories must reside within `implementations/[target-name]/`.

- **`/[target-name]/config`**: Tech stack definition (e.g., "Language: Go 1.22", "Framework: Gin").
- **`/[target-name]/.spd/`**: SPD state and control files.
    - `state.yaml`: Machine-readable sync state with spec hashes and module statuses.
    - `tasks.yaml`: Builder task backlog and execution state.
    - `reviews/`: Reviewer agent output artifacts.
- **`/[target-name]/tech_assumptions.md`**: Ledger of technical assumptions (See Section 6).
- **`/[target-name]/Makefile`**: Standard execution interface (See Section 7).
- **`/[target-name]/src/`**: The generated output. **MUST** structurally correspond to the `/docs/specs/[module]/` hierarchy, mapped through the platform skill's structural rules.

> **Structural Correspondence Rule:** The Builder MUST apply the mapping function defined by the target's `@shared/skills/platforms/[platform]` skill. For example, a Java/Spring platform skill defines that `docs/specs/[module]/models/Debt.md` maps to `src/main/java/{base_package}/[module]/models/Debt.java`. The platform skill MUST define every layer mapping explicitly.

---

## 2. The Compilation Algorithm

When instructed to synchronize or compile, the Builder MUST follow these phases:

### Phase I: Parse & Validate (Formal Layer)
1. Scan the requested module in `docs/specs/[module]`.
2. Validate module structure against the Module Validity rules (Section 1.1).
3. Resolve all `[[...]]` references. Every reference MUST point to an existing file.
4. Map all referenced models to create the **Dependency Graph**.
5. Load all **Shared Skills** referenced in the module's `implementation/mapping.md` and the global `specs/shared/implementation.md`.
6. If a cross-module dependency exists, verify the contract by ensuring the external module declares the referenced model/event in its `definitions/` or `events/`.

### Phase II: Contract Check (Formal Layer)
- **Zero Technical Leakage:** Files in `commands/`, `queries/`, `models/`, and `projections/` **MUST NOT** mention tech-specific terms (e.g., JSON, SQL, React, JWT). Use pure domain terms (*Store*, *Encrypt*, *Display*, *Token*).
- **SRP (Single Responsibility):** Every **declarative unit** within a spec file maps to at least one corresponding code artifact in `src/`. A spec file MAY decompose into multiple artifacts (e.g., one `behaviors/` file generates multiple test methods; one `implementation/mapping.md` generates migrations and config). The decomposition rules are defined by the platform skill.
- **Skill Coverage:** Every technical requirement in the specs MUST be covered by at least one assigned skill. If a requirement has no covering skill, the Builder MUST record an assumption (Section 6) and halt if in strict mode.

### Phase III: Synthesize (Interpretive Layer)
- **File Mapping:** Generate code by applying the platform skill's structural mapping to each spec file.
- **Skill Synthesis:** Inject technical boilerplate (SQL queries, HTTP decorators, etc.) by interpreting the business logic through the lens of the chosen Shared Skill.
- **Acknowledgment of Interpretation:** This phase requires LLM synthesis. The Builder MUST make conservative choices and record any ambiguity in `tech_assumptions.md`.

### Phase IV: Verify (Formal Layer)
- **Test Generation:** Compile the rules defined in `behaviors/` into the target's native testing framework.
- **Execution:** Execute the tests against the synthesized code.
- **Failure Handling:** On failure, emit a `BUILD_FAILURE` report to `.spd/reports/`. The Builder MUST NOT auto-correct by modifying specs. It MAY attempt to fix implementation code within the constraints of the spec, up to a configurable retry limit.

---

## 3. Specification Standards (Syntax & Logic)

### Spec File Structure
To maximize formal validity and minimize interpretive variance, every spec file SHOULD follow this section order:

```markdown
# Title

## Goal (or Description)
One-sentence purpose.

## Invariants
- Constraints that are always true for this entity/command.

## Preconditions
- What must hold before execution.

## Postconditions
- What must hold after successful execution.

## Effects
- Side effects, including events emitted or state changed.

## Errors
- Domain errors this command/query may raise.
```

Commands and Queries MAY additionally include:
```markdown
## Input
- Parameter list with types.

## Flow
- Numbered steps (interpretive layer).
```

### Cross-Referencing Syntax
- To reference a model within the same module: `[[models/User]]`
- To reference a cross-module model: `[[specs/inventory/models/Item]]`
- To reference a global skill: `@shared/skills/persistence/postgresql`

**Clean Spec Smell:** If you find yourself writing conditional flow logic inside a `models/` file, move it to `commands/`. If a `commands/` file exceeds 50 lines of natural language flow, decompose it into smaller commands or extract shared invariants.

---

## 4. The Sync Protocol (`.spd/` directory)

Each implementation tracks its own "Freshness" through machine-readable files in `.spd/`.

### 4.1. State File (`.spd/state.yaml`)
```yaml
last_sync: "2026-05-21T15:24:56Z"
target_stack: "Java 21 / Spring Boot"
modules:
  identity:
    status: SYNCED
    spec_hash: "sha256:a1b2c3..."
    current_hash: "sha256:a1b2c3..."
  debts:
    status: OUTDATED
    spec_hash: "sha256:old123..."
    current_hash: "sha256:new456..."
pending_diffs:
  - path: "debts/commands/CreateDebt.md"
    change: "Added currency validation"
```

- `spec_hash`: SHA-256 of the module's spec files at the time of last successful sync.
- `current_hash`: SHA-256 of the module's spec files now.
- `status`: `SYNCED`, `OUTDATED`, `PENDING_REVIEW`, or `BUILD_FAILURE`.

### 4.2. Tasks File (`.spd/tasks.yaml`)
For initial development or major refactoring, the Architect generates a task list:
```yaml
tasks:
  - id: 1
    description: "Generate Spring Boot pom.xml and application entry point"
    type: INFRASTRUCTURE
    status: DONE
    spec_refs: []
  - id: 2
    description: "Implement Debt domain model"
    type: MODEL
    status: PENDING_TO_CODE
    spec_refs: ["docs/specs/debts/models/Debt.md"]
    dependencies: []
```

**Status Lifecycle:**
- `PENDING_TO_CODE`: The Builder must write the code and verify it compiles/tests pass. Once completed, the Builder updates the status to `PENDING_REVIEW`.
- `PENDING_REVIEW`: The Reviewer agent (Section 9.3) validates the implementation against the spec and skills. If approved, status becomes `DONE`. If rejected, status returns to `PENDING_TO_CODE` with review notes.
- `DONE`: The task is fully completed and verified.
- `BLOCKED`: The Builder encountered an ambiguity and halted. Requires Architect or Human intervention.

### 4.3. Review Artifacts (`.spd/reviews/`)
Each review produces a timestamped file:
```markdown
# Review: 2026-05-21-debts-models

## Task: 2
- Status: REJECTED
- Violations:
  1. Missing `direction` field in Debt.java (spec defines it, model omits it)
  2. Technical leakage: `import com.fasterxml.jackson` found in domain model
```

---

## 5. Agent Behavioral Laws

1. **The "Halt and Catch Fire" Rule:**
   - In **Interactive Mode** (human-in-the-loop): If a spec is ambiguous, contradicts another spec, or a referenced Skill is missing, STOP. Write a `BLOCKER.md` in `.spd/` and wait for human input.
   - In **Autonomous Mode** (CI/CD): Emit a `WARNING` to `.spd/warnings.log`, make the most conservative assumption, record it in `tech_assumptions.md`, and continue. Never hallucinate a business rule.

2. **Idempotency:** Re-running the synthesis on unchanged specs and locked assumptions MUST result in exactly zero changes to the `src` folder. Idempotency is achieved by:
   - Freezing all assumptions in `tech_assumptions.md` after the first run.
   - Using deterministic platform skills.
   - The Builder MUST load existing assumptions as constraints before synthesis.

3. **Security First:** Apply the most restrictive security patterns defined in `@shared/skills/security/` unless explicitly overridden.

4. **Role Containment:**
   - The Architect MUST NOT read or write `implementations/`.
   - The Builder MUST NOT read or write `docs/specs/`.
   - The Reviewer is read-only across both layers.

---

## 6. Technical Coherence & Assumptions

When generating code, if a technical choice is required but no specific Skill dictates it:

- **Architectural Consistency:** Do not mix multiple technologies that serve the same purpose. Maintain a unified stack within the implementation.
- **Technical Completeness:** The Builder MUST ensure that every implementation is technically complete. This includes generating all necessary configuration files (e.g., `tsconfig.json`, `package.json`, `Makefile`, `.gitignore`) required by the chosen tech stack and Shared Skills.
- **The Assumptions Ledger:** You **MUST** record any unresolved technical requirement in `/implementations/[target]/tech_assumptions.md`.
    - *Format:* `[Date] - [Assumption Made] - [Reasoning] - [Affected Modules]`
    - Assumptions are for **skill gaps**, not creative freedom. If a skill exists that covers the choice, the Builder MUST follow it.
- **Promotion Workflow:** This ledger allows humans to review assumptions. Once validated, the human or agent will promote the assumption into a formal `@shared/skills/` file, and remove it from the ledger.

### Skill Precedence Rules
When multiple skills apply to the same concern, resolve conflicts in this order:
1. **Module-specific `implementation/mapping.md` overrides `specs/shared/implementation.md`.**
2. **An explicitly referenced skill overrides an inherited skill of the same category.**
3. **If two explicitly referenced skills conflict, the one referenced later in the file wins.**
4. **If resolution still fails, HALT (Halt and Catch Fire Rule).**

---

## 7. Local Execution & Tooling (Makefile)

Every implementation MUST be "ready-to-run" for a human developer. The Builder is responsible for generating and maintaining a standard `Makefile` in the root of `/implementations/[target]/`.

### Mandatory Makefile Targets

#### Build & Test
- `make build`: Compiles, transpiles, or packages the project. Must handle dependency installation (e.g., `go mod tidy`, `mvnw compile`, `cargo build`).
- `make test`: Executes all unit and integration tests derived from the specs. Must spin up Testcontainers or equivalent for integration tests.

#### Local Development Lifecycle
- `make start-core`: Spins up the core infrastructure (PostgreSQL, RabbitMQ, Redis, observability stack) using Docker Compose. Does NOT start the application service. After this, the environment is ready for a service to connect.
- `make start`: Starts everything — infrastructure AND the application service(s) — in a single command. The API is ready to receive requests after this completes.
- `make stop`: Gracefully stops the application service(s) and infrastructure.
- `make restart`: Equivalent to `make stop && make start`.

#### Individual Service Control (for multi-binary targets)
- `make start-api`: Starts only the REST API service (ApiRunner).
- `make start-worker`: Starts only the background worker / event subscriber process (WorkerRunner).

#### Utilities
- `make migrate`: Runs database migrations (Flyway, golang-migrate, etc.) against the local PostgreSQL.
- `make seed`: Seeds the local database with demo data for development.
- `make logs`: Tails logs from all running containers and services.
- `make clean`: Removes build artifacts, Docker volumes, and resets the local environment.

#### Benchmarking
- `make benchmark`: Executes the standardized benchmark protocol defined in `@shared/skills/testing/benchmarking` against a fresh local environment. Emits `benchmark-report.json`.
- `make benchmark-compare BASE=[TargetA] TARGET=[TargetB]`: Compares two benchmark reports and outputs a diff analysis.

> [!NOTE]
> The specific underlying commands are determined by the config and the Shared Skills. If a build tool is chosen but not strictly defined, log it in `tech_assumptions`.

---

## 8. Repository Hygiene (.gitignore Management)

The repository must remain pristine. It should strictly contain the declarative original content (`docs/`) and the declarative code implementations. No build artifacts, execution-derived temporary files, or OS/IDE metadata should ever be committed.

- **Agent Responsibility:** Each implementation must independently manage its own `.gitignore` logic. The agent MUST ensure that framework-specific artifacts are properly ignored.
- **The Append-Only Rule:** When updating the `.gitignore`, the agent must **APPEND** missing rules. Never remove or truncate existing rules.
- **Contextual Awareness:** If the config specifies Java, ensure `*.class` and `target/` are ignored. If Node, `.env` and `node_modules/`, etc.

---

## 9. Agent Workflows & Core Commands

The system separates the workload into three distinct agent personas to prevent contextual contamination:

### 9.1. The Architect (Spec Agent)
- **Domain:** Exclusively operates within the `/docs` folder.
- **Workflow:** You interact with The Architect to brainstorm, design, and update specs.
- **Action:** If you say "Add a reset password feature", The Architect updates the `models`, `commands`, and `behaviors` in the `auth` module. It never touches implementation code.
- **Output:** Modified specs, updated task lists in `.spd/tasks.yaml`, and `BLOCKER.md` files when halted.

### 9.2. The Builder (Implementation Agent)
- **Domain:** Exclusively operates within `/implementations/[target]`.
- **Workflow:** Triggered when the specs change. It reads the `.spd/state.yaml` to identify Spec Hash mismatches, projects the updated specs into the `src/` folder, and runs `make test`. If tests fail, it adjusts its projection—it **never** modifies the specs.
- **Rule of Skill Priority:** The Builder MUST prioritize using existing Shared Skills over inventing new technical solutions or assumptions.

### 9.3. The Reviewer (Validation Agent)
- **Domain:** Read-only across `/docs` and `/implementations/[target]/src`.
- **Workflow:** Triggered when a Builder marks a task as `PENDING_REVIEW`. It compares the implementation against the declarative specs and shared skills.
- **Action:** Writes review reports to `.spd/reviews/`. Can approve (task → `DONE`) or reject (task → `PENDING_TO_CODE` with notes).
- **Checklist:**
  - [ ] Every model property has a corresponding field in the implementation.
  - [ ] Every command emits the events it claims to emit.
  - [ ] No technical leakage in domain layers.
  - [ ] The Mirror Rule (structural correspondence) is satisfied.
  - [ ] No imports from other modules' `domain/` packages.

### 9.4. The Plan Stage (Skill Architect)
Before the Builder executes, an architectural planning stage may occur to define new Skills or update existing ones if the current "Physics" of the system do not support the requirements.

### 9.5. Directives

When communicating with the agents, humans or CI/CD pipelines will use these directives:

- **`INITIALIZE [Context]`**: Generate the `docs` folder structure, base text files, initial shared skills, and base `.gitignore`.
- **`FREEZE [Module]`**: Lock a module's specs. Generate `.spd/manifest.json` with resolved hashes and dependency graph. No Builder should act on an unfrozen module.
- **`LINT`**: Validate all specs for broken references, missing fields, and contradictions without generating code.
- **`SYNCHRONIZE [Target]`**: Trigger The Builder. Update the implementation `src`, `.spd/state.yaml`, `Makefile`, and `.gitignore` to match the latest specs.
- **`ADVANCE [Target] [TaskId?]`**: Instruct the Builder to execute tasks. If `TaskId` is omitted, the Builder picks the first non-`DONE` task. Multiple Builders MAY operate on different tasks in parallel.
- **`REVIEW [Target] [TaskId?]`**: Trigger The Reviewer. If `TaskId` is omitted, review all `PENDING_REVIEW` tasks.
- **`AUDIT`**: Compare `docs/` against all `implementations/` and report **OUTDATED** modules or broken assumptions.
- **`BENCHMARK [Target A] vs [Target B]`**: Execute the standardized benchmark protocol on both targets (or reuse existing `benchmark-report.json` files), validate schema compatibility, and output a comparison report analyzing latency, throughput, and event propagation across all 5 phases.
- **`REFACTOR SKILL [Skill Path]`**: Update a technical law and systematically propagate the architectural change to all dependent target implementations.
- **`PROMOTE [AssumptionId]`**: Move an assumption from `tech_assumptions.md` to a formal `@shared/skills/` file.

---

## Appendix A: Glossary

- **SPD**: Spec-Projected Development.
- **Formal Layer**: The machine-validatable aspects of specs (topology, references, skill assignments).
- **Interpretive Layer**: The natural language aspects of specs requiring LLM synthesis (flow descriptions, business rules).
- **Skill**: A technical law in `/shared/skills/` defining how to implement a concern (e.g., PostgreSQL persistence).
- **Spec**: A declarative document in `/specs/` defining what the system should do.
- **Projection**: An implementation in `/implementations/[target]/` generated from specs and skills.
- **Module**: An atomic business unit in `/specs/[module]/` with a bounded context.
