# Skill: Module Boundaries

## Purpose
Defines the rules for cross-module communication and dependency management.

## Allowed Dependencies
- A module MAY depend on another module's `models/` (read-only).
- A module MAY depend on another module's `events/` (as a consumer).
- A module MAY NOT depend on another module's `commands/`, `queries/`, or `projections/`.

## Dependency Direction
The dependency graph MUST be acyclic. If Module A depends on Module B, Module B MUST NOT depend on Module A.

## Current Dependency Graph
```
identity -> (none)
people -> identity
payments -> debts
debts -> people, identity, payments (events only)
```

## Violation Detection
If a spec file references a disallowed layer (e.g., debts/commands importing people/commands), the Builder MUST halt and report a boundary violation.
