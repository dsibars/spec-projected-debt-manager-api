# Event Handler: Sync Person

## Goal
To maintain a local Read Model of People within the Debts module, ensuring high availability and zero synchronous coupling.

## Subscribes To
- `people.PersonCreated`
- `people.PersonArchived`

## Flow (PersonCreated)
1. Receive event payload: `id`, `name`.
2. Create or update the [[models/PersonReadModel]] with the provided `id` and `name`.
3. Set `isArchived` to `false`.
4. Persist to the local store.

## Flow (PersonArchived)
1. Receive event payload: `id`.
2. Find the [[models/PersonReadModel]] by `id`.
3. Set `isArchived` to `true`.
4. Persist to the local store.
