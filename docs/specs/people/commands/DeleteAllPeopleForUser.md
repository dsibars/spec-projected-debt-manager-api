# Command: Delete All People For User

## Goal
Bulk deletion of a user's directory during a GDPR purge.

## Input
- `tenantId`: `uuid` (The User whose data must be purged)

## Preconditions
- The tenant must exist (this command is triggered by an authorized purge event).

## Flow
1. Identify all [[models/Person]] records where `tenantId` matches.
2. Hard-delete these records from the Store.
3. Emit `PeoplePurged` event.
4. Emit `ModuleDataPurged` event (payload: `moduleId: "people"`, `userId: tenantId`).

## Postconditions
- No `Person` records exist for this `tenantId`.

## Effects
- Emits: `PeoplePurged` (payload: `tenantId`)
- Emits: `ModuleDataPurged`

## Errors
- (None expected; idempotent if no records exist.)

## Result
- `void`
