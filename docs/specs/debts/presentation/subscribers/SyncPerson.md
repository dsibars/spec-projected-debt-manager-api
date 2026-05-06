# Subscriber: Sync Person

## Goal
To adapt incoming Person events to the local synchronization command.

## Subscribes To
- `people.PersonCreated`
- `people.PersonArchived`

## Adapts
- [[../../commands/UpdatePersonReadModel]]

## Flow (PersonCreated)
1. Receive **`EventEnvelope`**. Extract `id` and `name` from `data`.
2. Call [[../../commands/UpdatePersonReadModel]] with `id`, `name`, and `isArchived: false`.

## Flow (PersonArchived)
1. Receive **`EventEnvelope`**. Extract `id` from `data`.
2. Call [[../../commands/UpdatePersonReadModel]] with `id` and `isArchived: true`.
