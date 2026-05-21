# Event: Debts Purged

## Description
Emitted by the Debts module after all financial records for a user have been hard-deleted in response to a GDPR purge request.

## Payload
- `eventId`: UUID
- `userId`: UUID
- `occurredAt`: DateTime

## Producers
- [[presentation/subscribers/OnIdentityPurgeRequested]]

## Consumers
- (None external; may be used for audit and completion tracking)
