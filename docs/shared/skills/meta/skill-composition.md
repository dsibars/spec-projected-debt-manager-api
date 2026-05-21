# Meta Skill: Skill Composition & Precedence

This skill defines how multiple Shared Skills interact when assigned to the same module or implementation.

## Purpose
Prevent conflicting technical directives and provide deterministic resolution when multiple skills cover the same concern.

---

## Skill Categories

Every skill belongs to exactly one category:
- **persistence**: Data storage (postgresql, migrations, repository-pattern)
- **platform**: Application framework (spring, axum, gin, ktor)
- **messaging**: Inter-service communication (rabbitmq, contracts)
- **security**: Auth and protection (jwt, hashing, oauth)
- **observability**: Monitoring and tracing (opentelemetry)
- **patterns**: Architectural patterns (cqrs, ddd, resilience)
- **devops**: Build and deployment (dockerization, configuration)
- **presentation**: API and UI standards (rest-api, openapi)
- **testing**: Test strategies and behavior projection
- **meta**: SPD toolchain rules (this file, review-protocol, spec-validation)

## The One-Skill-Per-Category Rule

Within a single module implementation, **only one skill per category may be operational** unless explicitly allowed by a meta-skill.

- You cannot assign both `@shared/skills/persistence/postgresql` and `@shared/skills/persistence/mysql` to the same module.
- You cannot assign both `@shared/skills/platforms/spring` and `@shared/skills/platforms/ktor` to the same target.

If multiple skills in the same category are referenced, the precedence rules apply.

---

## Precedence Rules

### Rule 1: Explicit Override Inherited
A skill referenced explicitly in a module's `implementation/mapping.md` overrides the same-category skill inherited from `specs/shared/implementation.md`.

**Example:**
```markdown
# specs/shared/implementation.md
- @shared/skills/persistence/postgresql

# specs/debts/implementation/mapping.md
- @shared/skills/persistence/postgresql
```
No conflict. But if debts wanted to opt into a different persistence skill, it would declare it explicitly.

### Rule 2: Later Reference Wins
If two skills of the same category are both explicitly referenced in the same file, the one referenced later in the file takes precedence.

**Example:**
```markdown
# specs/debts/implementation/mapping.md
- @shared/skills/persistence/postgresql
- @shared/skills/persistence/cockroachdb
```
Result: `cockroachdb` wins because it appears later.

### Rule 3: Module Overrides Global
A module's skill assignment overrides the global baseline for that module only.

### Rule 4: Halt on Unresolvable Conflict
If two skills define mutually exclusive behaviors and none of the above rules resolve the conflict, compilation MUST halt with a `SKILL_CONFLICT` error.

**Example of unresolvable conflict:**
- Skill A says: "All IDs must be UUID strings."
- Skill B says: "All IDs must be auto-incrementing integers."
- No precedence rule can reconcile this. HALT.

---

## Skill Interface Contract

Every operational skill MUST declare its interface in its header:

```markdown
# Skill: PostgreSQL Persistence

## Category: persistence
## Provides:
- Table schema syntax
- Column type mapping
- Index declaration format
## Conflicts With:
- mysql, sqlite, mongodb (same category)
## Depends On:
- @shared/skills/meta/type-mapping
```

This allows the LINT phase to validate skill compatibility before synthesis.

---

## Promotion from Assumptions

When an assumption from `tech_assumptions.md` is promoted to a skill:
1. Create the skill file in `docs/shared/skills/[category]/[name].md`.
2. Declare its category, provides, conflicts, and dependencies.
3. Remove the assumption from all `tech_assumptions.md` files.
4. Reference the new skill in `specs/shared/implementation.md` or module-specific mappings.
5. Run `LINT` to validate the new skill integrates correctly.
