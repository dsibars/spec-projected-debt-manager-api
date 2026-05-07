# Model: Event Envelope

This model defines the strict JSON wrapper schema that must be used whenever a Domain Event is serialized and dispatched to the Event Bus (e.g., RabbitMQ).

## Properties
- `eventId`: UUID (A globally unique identifier for this specific event occurrence, used for Inbox idempotency)
- `occurredOn`: Instant (The exact UTC timestamp the event happened)
- `eventType`: String (The fully qualified event name, e.g., `people.PersonCreated`)
- `metadata`: Object (System-level contextual data)
  - `tenantId`: UUID (Mandatory. The ID of the Tenant context in which this event occurred. This ID MUST be used in the Messaging Routing Key for tenant isolation).
  - `userId`: UUID (The ID of the User who triggered this event).
  - `correlationId`: UUID (To trace a single flow across multiple modules).
- `payload`: Object (The domain-specific data, as defined in the `## Emits` section of the logic specifications)

## Constraints
- This envelope guarantees that all distributed modules can correctly parse, route, and deduplicate events without knowing the inner payload structure beforehand.
