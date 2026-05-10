# Event: People Purged

## Description
Emitted by the People module after all person records for a specific user have been hard-deleted as part of an identity purge.

## Payload
- `eventId`: UUID
- `tenantId`: UUID (The ID of the user whose data was purged)
- `occurredAt`: DateTime

## Producers
- [[presentation/subscribers/OnIdentityPurgeRequested]]

## Consumers
- (None yet; used for system-level audit)
