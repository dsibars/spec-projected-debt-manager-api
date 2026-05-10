# Shared Event: Module Data Purged

## Description
A common event emitted by any module after it has successfully completed a data purge request (e.g., GDPR right to erasure).

## Payload
- `eventId`: UUID
- `moduleId`: String (The name of the module that performed the purge)
- `tenantId`: UUID (The ID of the user whose data was purged)
- `occurredAt`: DateTime

## Producers
- [[specs/people/presentation/subscribers/OnIdentityPurgeRequested]]
- [[specs/debts/presentation/subscribers/OnIdentityPurgeRequested]]

## Consumers
- (None yet; used for system-level audit and completion tracking)
