# Event: Identity Purge Requested

## Description
Emitted when a user requests complete data deletion (GDPR right to erasure).

## Payload
- `eventId`: UUID
- `userId`: UUID
- `tenantId`: UUID
- `requestedAt`: DateTime

## Producers
- [[commands/DeleteUser]]

## Consumers
- [[specs/debts/presentation/subscribers/OnIdentityPurgeRequested]]
