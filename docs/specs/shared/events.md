# Shared: Event Standards

## Event Envelope
All domain events MUST strictly follow the schema defined in [[models/EventEnvelope]].

## Event Naming Convention
- Format: `[ModuleName].[PastTenseVerb]`
- Examples: `Debt.Registered`, `Person.Created`, `Payment.Applied`

## Event Versioning
- The `version` field starts at 1.
- Breaking schema changes require a new event type (e.g., `Debt.RegisteredV2`).
- Old event versions MUST be supported by consumers for at least 2 deployment cycles.

## Event Idempotency
- Consumers MUST handle duplicate events gracefully.
- The `eventId` is the idempotency key.

## Event Ordering
- Events within the same aggregate MUST be processed in order.
- Cross-aggregate event ordering is not guaranteed.

## Event Persistence
- Events MUST be persisted to the Outbox store before being published to the message broker.
- The Outbox pattern is mandatory for all event producers.
