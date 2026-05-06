# Model: Event Envelope

This model defines the strict JSON wrapper schema that must be used whenever a Domain Event is serialized and dispatched to the Event Bus (e.g., RabbitMQ).

## Properties
- `eventId`: UUID (A globally unique identifier for this specific event occurrence, used for Inbox idempotency)
- `occurredOn`: Instant (The exact UTC timestamp the event happened)
- `eventType`: String (The fully qualified event name, e.g., `people.PersonCreated`)
- `payload`: Object (The domain-specific data, as defined in the `## Emits` section of the logic specifications)

## Constraints
- This envelope guarantees that all distributed modules can correctly parse, route, and deduplicate events without knowing the inner payload structure beforehand.
