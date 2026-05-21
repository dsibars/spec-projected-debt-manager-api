# Skill: Behavior-to-Test Projection

## Category: testing
## Provides:
- Behavior Projection
## Conflicts With:
- strategy
## Depends On:
- None explicitly declared


## Purpose
Defines how natural language behaviors in `docs/specs/[module]/behaviors/` are projected into executable tests.

## Input Format
Behaviors are written in Given/When/Then format in markdown files.

## Projection Rules

### 1. Test File Naming
- Source: `behaviors/debt_management.md`
- Target: `tests/DebtManagementTest.[ext]` (language-specific)

### 2. Scenario Mapping
Each `### Scenario: ...` block becomes one test method/function.

### 3. Step Mapping
| Behavior Step | Test Code Responsibility |
|---------------|-------------------------|
| `Given [state]` | Arrange: setup mocks, seed database, create fixtures |
| `When [action]` | Act: invoke the command/query under test |
| `Then [result]` | Assert: verify state, check return values |
| `And [result]` | Additional assertions |

### 4. Cross-Module Scenario Rule
If a scenario references models from another module (e.g., "Given a person exists"), the test MUST use the module's public factory/test helper, not internal structures.

### 5. Async Assertion Rule
For scenarios involving event-driven updates, tests MUST use polling with:
- Default timeout: 5 seconds
- Poll interval: 100ms
- Failure message: "Eventual consistency failure: [expected state] not reached"

### 6. Idempotency Test Rule
Every integration test for an Event Subscriber MUST execute the same event TWICE to verify that the Inbox Pattern is correctly implemented.

### 7. Naming Convention
Tests MUST follow: `given[State]_when[Action]_then[ExpectedResult]`