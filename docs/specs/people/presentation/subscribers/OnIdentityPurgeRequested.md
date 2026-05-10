# Subscriber: On Identity Purge Requested

## Goal
To fulfill the GDPR "Right to be Forgotten" by deleting all personal data associated with a user.

## Subscribes To
- `identity.IdentityPurgeRequested`

## Flow
1. Receive **`EventEnvelope`**. Extract `targetUserId` from the payload.
2. Verify that the event `metadata.tenantId` matches an authorized administrative ID (or the system ID).
3. Call the Internal Command [[commands/DeleteAllPeopleForUser]] (userId: targetUserId).
4. Perform a hard-delete of all Person aggregates where `tenantId = targetUserId`.
5. Emit **`ModuleDataPurged`** event (payload: `moduleId: "people", userId: targetUserId`).
6. Emit `PeoplePurged` event to confirm completion.
