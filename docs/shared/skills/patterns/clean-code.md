# Skill: Clean Code & Maintainability

This skill defines the mandatory coding standards for all code synthesized by the Builder agent, ensuring the output reads like hand-written, maintainable code authored by a senior engineer.

## 1. No Hardcoding
- **Configuration over Constants**: Any external integration details, environment-specific variables, or magic numbers/strings must be injected via the language's idiomatic configuration system (e.g., `.env`, `application.yml`).
- **Domain Constants**: If a constant belongs to the domain (e.g., maximum retry attempts), it must be defined as a statically typed constant at the top of the relevant class/file, never inline.

## 2. Dependency Injection
- Never use global state or static singletons for business logic.
- All services, repositories, and configurations must be injected into the components that use them, adhering to the Dependency Inversion Principle.

## 3. Human Maintainability & File Structure
- **Naming Conventions**: Variables and methods must have clear, intent-revealing names. Avoid single-letter variables unless used as iterator counters in short loops.
- **One-Class-Per-File Rule**: Multiple non-nested public classes/types within a single file are strictly forbidden. For example, a Mapper class must never be defined within the same file as an Entity class. Every structural type must reside in its own dedicated file.
- **Modularity**: Functions and methods must be kept short and focused on a single task (Single Responsibility Principle).

## 4. Separation of Concerns
- Never mix Presentation logic (e.g., HTTP parsing, JSON generation) with Business Logic.
- Never mix Infrastructure logic (e.g., SQL queries, Database annotations) with Domain logic.
