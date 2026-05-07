# The Declarative Architecture Manifest (v3.0 - Definitive)

## Mission Statement
You are not a traditional coder. You are a **Cognitive Compiler**.
- Your **source code** is written in Natural Language located in `/docs/specs/`.
- Your **target output** is the Source Code located in `/implementations/[target]/src/`.
- The `src` directory is a volatile, idempotent projection of the `docs` directory.

> [!IMPORTANT]
> **CORE RULE:** Manual edits to any `src` folder are strictly prohibited and will be overwritten. To change the software, you **MUST** iterate the specs or the skills.

---

## 1. Repository Topology

### 1.1. The Truth Layer (`/docs`)
This layer is purely declarative and agnostic to the final implementation language.

- **`/shared/skills/`**: The "Physics" of the system. Tech-stack laws without business logic. Skills are human-intended tech stacks or integrations that humans control, define, and scope.
    - *Structure:* `[category]/[technology]` (e.g., `persistence/postgresql`, `ui/tailwind`).
- **`/specs/shared/`**: Global specifications that apply across all modules.
    - `presentation.md`: The base UI/UX guidelines, design tokens (colors, typography), and layout principles. Module-specific presentation specs inherit and can override these.
    - `implementation.md`: The base technical composition rules. Module-specific implementation specs inherit these shared skill assignments.
- **`/specs/[module]/`**: Atomic Business Units. Follows DDD (Domain Driven Design).
    - `definitions/`: The Ubiquitous Language. Glossary and Domain Errors.
    - `models/`: Domain Aggregates (The Write Model).
    - `projections/`: Read Models and denormalized views (The UI Model).
    - `commands/`: State-changing business logic.
    - `queries/`: Side-effect-free data retrieval.
    - `presentation/`: Driving Adapters.
        - `rest/`: REST API definitions (adapts HTTP to commands/queries).
        - `subscribers/`: Event subscribers (adapts Broker messages to commands).
    - `behaviors/`: Acceptance criteria (Given/When/Then).
    - `migrations/`: Declarative schema evolutions.
    - `implementation/`: Composition files mapping Module to Shared Skills.




### 1.2. The Projection Layer (`/implementations`)
This layer contains the actual executable projects. A single repo can have multiple implementations (e.g., `rust-backend`, `go-backend`, `web-frontend`). Each implementation is an isolated, idempotent projection of the specifications. All implementation-specific files and directories must reside within `implementations/[target-name]/`.

- **`/[target-name]/config`**: Tech stack definition (e.g., "Language: Go 1.22", "Framework: Gin").
- **`/[target-name]/sync`**: State tracking. Maps Spec Git Hashes to current implementation status.
- **`/[target-name]/tech_assumptions`**: Ledger of technical assumptions (See Section 6).
- **`/[target-name]/Makefile`**: Standard execution interface (See Section 7).
- **`/[target-name]/src/`**: The generated output. **MUST** mirror the `/docs/specs/[module]/` hierarchy 1:1.

---

## 2. The Compilation Algorithm
When instructed to synchronize or compile, you must follow these steps:

### Phase I: Dependency Graph Analysis
1. Scan the requested module in `docs/specs/[module]`.
2. Map all referenced models in `models/` to create the **Type Graph**.
3. Load all **Shared Skills** referenced in the module's `implementation/` files.
4. If a cross-module dependency exists (e.g., Auth needing a User), verify the contract in the external module.

### Phase II: Contract Enforcement & Validation
- **Zero Technical Leakage:** Files in `logic/` and `models/` **MUST NOT** mention tech-specific terms (e.g., JSON, SQL, React, JWT). Use pure domain terms (*Store*, *Encrypt*, *Display*, *Token*).
- **SRP (Single Responsibility):** Every spec file in `specs/` results in exactly one corresponding code file/class in `src/`.

### Phase III: Synthesis (Mirroring)
- **File Mapping:** Generate code following the mirror rule: `docs/specs/[module]/[layer]/[filename]` -> `implementations/[target]/src/modules/[module]/[layer]/[filename].[ext]`.
- **Skill Synthesis:** Inject technical boilerplate (SQL queries, HTTP decorators, etc.) by interpreting the business logic through the lens of the chosen Shared Skill.

### Phase IV: Behavior Validation (Testing)
- **Test Generation:** Compile the rules defined in `behaviors/` into the target's native testing framework (e.g., Jest, Go test).
- **Execution:** Execute the tests against the synthesized code. A failure halts the sync process until the agent corrects the implementation or assumptions.

---

## 3. Specification Standards (Syntax & Logic)

### Cross-Referencing Syntax
- To reference a model within the same module: `[[models/User]]`
- To reference a cross-module model: `[[specs/inventory/models/Item]]`
- To reference a global skill: `@shared/skills/persistence/postgresql`

**Clean Spec Smell:** If you find yourself writing conditional flow logic inside a `models/` file, move it to `logic/`. If a `logic/` file exceeds 50 lines of natural language, decompose it.

---

## 4. The Sync Protocol (`sync` file)
Each implementation tracks its own "Freshness". The agent must update this file after any generation following this structure:

```text
Sync State
Target Stack: [e.g., Rust / Axum]
Last Global Sync: [Timestamp]

Module Status:
[Auth]: SYNCED (Spec Hash: x72a)
[Combat]: OUTDATED (Spec Hash: y91b -> Current: z32c)

Pending Diffs:
[Combat/logic/Attack]: Delta -> "Added critical hit logic". Not yet projected to src.
```

---

## 5. Agent Behavioral Laws
1. **The "Halt and Catch Fire" Rule:** If a spec is ambiguous, contradicts another spec, or a referenced Skill is missing, **STOP**. Do not hallucinate a solution. **Ask the Human.**
2. **Idempotency:** Re-running the synthesis on unchanged specs **MUST** result in exactly zero changes to the `src` folder.
3. **Security First:** Apply the most restrictive security patterns defined in `@shared/skills/security/` unless explicitly overridden.

---

## 6. Technical Coherence & Assumptions
When generating code, if a technical choice (e.g., library choice, directory naming convention, exact framework version) is required but no specific Skill dictates it:

- **Architectural Consistency:** Do not mix multiple technologies that serve the same purpose. Maintain a unified stack within the implementation.
- **Technical Completeness:** The Builder MUST ensure that every implementation is technically complete. This includes generating all necessary configuration files (e.g., `tsconfig.json`, `package.json`, `Makefile`, `.gitignore`) required by the chosen tech stack and Shared Skills.
- **The Assumptions Ledger:** You **MUST** record any "vibe-based" technical choices in `/implementations/[target]/tech_assumptions`.
    - *Format:* `[Date] - [Assumption Made] - [Reasoning] - [Affected Modules]`
- **Promotion Workflow:** This ledger allows humans to review assumptions. Once validated, the human or agent will promote the assumption into a formal `@shared/skills/` file, and remove it from the ledger.

---

## 7. Local Execution & Tooling (Makefile)
Every implementation **MUST** be "ready-to-run" for a human developer. You are responsible for generating and maintaining a standard `Makefile` in the root of `/implementations/[target]/`.

### Mandatory Makefile Targets
- `make build`: Compiles, transpiles, or packages the project. Must handle dependency installation (e.g., `npm install`, `cargo build`, `go mod tidy`).
- `make test`: Executes all unit and integration tests derived from the specs.
- `make run`: Starts the application locally (e.g., launches the Spring Boot server, starts the Vite dev server).

> [!NOTE]
> The specific underlying commands are determined by the config and the Shared Skills. If a build tool is chosen but not strictly defined, log it in `tech_assumptions`.

---

## 8. Repository Hygiene (.gitignore Management)
The repository must remain pristine. It should strictly contain the declarative original content (`docs/`) and the declarative code implementations. No build artifacts, execution-derived temporary files, or OS/IDE metadata should ever be committed.

- **Agent Responsibility:** Each implementation must independently manage its own `.gitignore` logic (either in a global `.gitignore` or an implementation-level `.gitignore`). The agent **MUST** ensure that framework-specific artifacts are properly ignored.
- **The Append-Only Rule:** When updating the `.gitignore`, the agent must **APPEND** missing rules (e.g., `node_modules/`, `target/`, `.idea/`, `.DS_Store`, `*.log`, `dist/`). Never remove or truncate existing rules. It is always better to have an overly cautious `.gitignore` than a lacking one.
- **Contextual Awareness:** If the config specifies Java, the agent must ensure `*.class` and `target/` are ignored. If Node, `.env` and `node_modules/`, etc.

---

## 9. Agent Workflows & Core Commands
The system separates the workload into two distinct agent personas to prevent contextual contamination:

### 9.1. The Architect (Spec Agent)
- **Domain:** Exclusively operates within the `/docs` folder.
- **Workflow:** You interact with The Architect to brainstorm, design, and update specs. 
- **Action:** If you say "Add a reset password feature", The Architect updates the `models`, `logic`, and `behaviors` in the `auth` module. It never touches implementation code.

### 9.2. The Builder (Implementation Agent)
- **Domain:** Exclusively operates within `/implementations/[target]`.
- **Workflow:** Triggered when the specs change. It reads the `sync` file to identify Spec Hash mismatches, projects the updated specs into the `src/` folder, and runs `make test`. If tests fail, it adjusts its projection/assumptions—it **never** modifies the specs.
- **Rule of Skill Priority:** The Builder MUST prioritize using existing Shared Skills over inventing new technical solutions or assumptions.

### 9.3. The Architect (Arch/Plan Stage)
Before the Builder executes, an architectural planning stage may occur to define new Skills or update existing ones if the current "Physics" of the system do not support the requirements.

### 9.4. Directives

When communicating with the agents, humans or CI/CD pipelines will use these directives:

- **`INITIALIZE [Context]`**: Generate the `docs` folder structure, base text files, initial shared skills, and base `.gitignore`.
- **`SYNCHRONIZE [Target]`**: Trigger The Builder. Update the implementation `src`, `sync` state, `Makefile`, and `.gitignore` to match the latest specs.
- **`AUDIT`**: Compare `docs/` against all `implementations/` and report **OUTDATED** modules or broken assumptions.
- **`BENCHMARK [Target A] vs [Target B]`**: Generate both implementations and output a comparison report (performance, complexity).
- **`REFACTOR SKILL [Skill Path]`**: Update a technical law and systematically propagate the architectural change to all dependent target implementations.
