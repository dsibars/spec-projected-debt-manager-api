# Command: Delete All People For User

## Goal
Bulk deletion of a user's directory during a GDPR purge.

## Input
- `tenantId`: `uuid` (The User whose data must be purged)

## Flow
1. Identify all [[models/Person]] records where `tenantId` matches.
2. Hard-delete these records from the Store.
3. Emit `PeoplePurged` event.

## Emits
- `PeoplePurged` (payload: `tenantId`)
