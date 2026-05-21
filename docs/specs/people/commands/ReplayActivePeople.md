# Command: Replay Active People

## Goal
Broadcast creation events for all current non-archived people.

## Input
- `requesterTenantId`: `uuid` (Optional filter; if omitted, replay all)

## Preconditions
- (None)

## Flow
1. Query Store for all [[models/Person]] where `isArchived` is `false`.
2. If `requesterTenantId` is provided, filter by that tenant.
3. For each record:
   - Emit `PersonCreated` with `id`, `name`, `email`, `phone`, and `tenantId`.

## Postconditions
- `PersonCreated` events have been emitted for all active people.

## Effects
- Emits: `PersonCreated` (multiple)

## Errors
- (None)

## Result
- `void`
