# Skill: Messaging - Event Contracts

This skill defines the structural laws and naming conventions for all asynchronous messages dispatched via the Event Bus.

## 1. The Global Envelope Law
All events published to the broker MUST strictly follow the schema defined in `docs/specs/shared/models/EventEnvelope.md`. 

The implementation MUST provide a generic `EventEnvelope<T>` type that wraps the domain-specific `payload`.

## 2. Naming Conventions
To ensure discoverability and predictable routing, events MUST use the following naming pattern:

`[module].[Entity][Action]`

- **Examples**: `identity.UserRegistered`, `people.PersonCreated`, `debts.DebtRegistered`.
- **Action Tense**: Always use the **Past Tense** to signify that the event has already occurred.

## 3. Versioning Strategy
- **Compatibility**: Payloads MUST be evolved in a backward-compatible manner (adding optional fields) whenever possible.
- **Breaking Changes**: If a breaking change is required, the `eventType` MUST be incremented with a version suffix.
    - **Example**: `identity.UserRegistered.v2`
- **Subscribers**: MUST be tolerant of unknown fields (Ignore unknown properties) during deserialization.

## 4. Routing Key Law
The messaging provider (e.g., RabbitMQ) MUST use routing keys derived from the envelope to enable tenant isolation:

`[tenantId].[module].[entity].[action]`

## 5. Serialization Standards
- **Format**: JSON (UTF-8).
- **Date/Time**: All timestamps MUST be ISO-8601 strings in UTC format.
- **Precision**: Monetary values MUST be serialized as integers (smallest currency unit) as defined in the models.
