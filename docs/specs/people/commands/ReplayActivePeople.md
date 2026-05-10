# Command: Replay Active People

## Goal
To broadcast creation events for all current non-archived people.

## Flow
1. Query Store for all [[models/Person]] where `isArchived` is `false`.
2. For each record:
   - Emit `PersonCreated` with `id` and `name`.

## Emits
- `PersonCreated` (multiple)
