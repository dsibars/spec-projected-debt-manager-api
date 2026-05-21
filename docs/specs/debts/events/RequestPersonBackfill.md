# Event: Request Person Backfill

## Description
Emitted by the Debts module on startup (or when needed) to request the People module replay all active Person records for hydration of the local PersonReadModel.

## Payload
- `eventId`: UUID
- `requesterModule`: String (e.g., "debts")
- `occurredAt`: DateTime

## Producers
- [[presentation/subscribers/RequestPersonBackfill]]

## Consumers
- [[specs/people/presentation/subscribers/ProcessPersonBackfill]]
