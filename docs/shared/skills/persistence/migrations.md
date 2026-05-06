# Skill: Migrations

This skill defines the abstract, technology-agnostic philosophy and requirements for managing structural or data state changes over time across any persistence layer.

## Core Concepts

1. **Definition of a Migration**: A migration is a strictly ordered script that alters the state of a persistence layer (e.g., creating a schema, adding an index, or backfilling data).
2. **Immutability Law**: Once a migration script has been committed and executed against any persistent environment, it is **strictly immutable**. You must NEVER modify an existing migration. Any corrections or structural rollbacks must be authored as a brand *new* migration script to be executed sequentially.
3. **Strict Ordering**: Migrations must follow a clear, ascending versioning scheme to guarantee deterministic execution across all environments.
4. **Execution Barrier**: The execution of pending migrations acts as a strict technical barrier. All pending migrations MUST be successfully executed against the target environment (including ephemeral test databases) *before* the application code is allowed to fully boot or accept traffic.
5. **Idempotency Guidance**: Where the underlying technology allows, migrations should be designed idempotently to ensure they can recover safely if a previous execution partially failed or was interrupted.
