# Definitions: Shared Kernel

## Core Terms

- **Value Object**: An immutable object defined by its attributes, not by an identity. Examples: `Currency`, `Email`.
- **Event Envelope**: The standard wrapper for all domain events, containing metadata (eventId, tenantId, occurredAt) and payload.
- **Module Data Purged**: A system-level event confirming that a module has completed GDPR data erasure for a user.
