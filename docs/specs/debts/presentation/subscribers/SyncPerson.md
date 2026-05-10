# Subscriber: Sync Person

## Goal
To adapt incoming Person events to the local synchronization command.

## Subscribes To
- `people.PersonCreated`
- `people.PersonUpdated`
- `people.PersonArchived`

## Adapts
- [[../../commands/UpdatePersonReadModel]]
- [[../../commands/PropagatePersonUpdate]]

## Flow (PersonCreated)
1. Receive **`EventEnvelope`**. Extract `id` and `name` from `data`.
2. Call [[../../commands/UpdatePersonReadModel]] with `id`, `name`, and `isArchived: false`.
3. Call [[../../commands/PropagatePersonUpdate]] with `id` and `name`.

## Flow (PersonUpdated)
1. Receive **`EventEnvelope`**. Extract `id` and `name` from `data`.
2. Call [[../../commands/UpdatePersonReadModel]] with `id`, `name`, and `isArchived: false`.
3. Call [[../../commands/PropagatePersonUpdate]] with `id` and `name`.

## Flow (PersonArchived)
1. Receive **`EventEnvelope`**. Extract `id` from `data`.
2. Call [[../../commands/UpdatePersonReadModel]] with `id` and `isArchived: true`.
