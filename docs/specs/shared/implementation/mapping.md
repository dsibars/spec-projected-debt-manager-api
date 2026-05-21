# Implementation: Shared Kernel Mapping

This module contains no persistent aggregates. All models here are value objects or standards referenced by other modules.

## Value Objects

### Currency
- Represented as a `VARCHAR(3)` string (ISO 4217 code) in all modules.
- Default: "USD"

### Email
- Represented as a `VARCHAR(255)` with format validation.
- Must be unique per tenant where applicable.

### EventEnvelope
- Not stored directly in a database table.
- Serialized as JSON in the `outbox_events.payload` column (see `@shared/skills/patterns/outbox-inbox-schema`).
