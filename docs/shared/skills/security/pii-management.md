# Skill: PII Management & GDPR Compliance

## Category: security
## Provides:
- Pii Management
## Conflicts With:
- hashing
- jwt
- oauth-integration
- token-cache
- traffic-governance
## Depends On:
- None explicitly declared


This skill defines the law for handling Personally Identifiable Information (PII) across the distributed ecosystem.

## Principles
1.  **Source of Truth**: The `Identity` module is the ONLY module authorized to store the "Primary Email" for account identification.
2.  **PII Shadowing**: Other modules (e.g., `People`) may store "Contact Info" only if strictly necessary for their domain logic.
3.  **Right to be Forgotten (Purge Law)**:
    - When a User is deleted from the `Identity` module, an `identity.IdentityPurgeRequested` event MUST be emitted.
    - Every module containing PII related to that `tenantId` MUST subscribe to this event and perform a hard-delete or anonymization of the PII data.
4.  **Encryption at Rest**: Any field labeled as `PII` in the models SHOULD be encrypted at rest using a per-tenant key or a global application key.

## Implementation Rules
- Modules must track which fields contain PII in their `models/` specifications.
- Purge handlers must be prioritized in the `subscribers/` layer.