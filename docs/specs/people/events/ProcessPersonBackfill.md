# Event Handler: Process Person Backfill Request

## Goal
To serve historical Person data to new subscribers by replaying Domain Events.

## Subscribes To
- `debts.RequestPersonBackfill` (or any global request for person backfills)

## Flow
1. Receive event payload.
2. Query the Store for all [[models/Person]] records where `isArchived` is `false`.
3. For each record, emit a `PersonCreated` event with the person's `id` and `name`.
4. (The Builder must handle this iteratively or via a batching mechanism to avoid out-of-memory errors on massive datasets).
