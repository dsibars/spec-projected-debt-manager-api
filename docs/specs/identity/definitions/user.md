# Definitions: Identity Domain

## Core Terms
- **User**: The technical identity authorized to access the system.
- **Tenant**: (Synonym for User in this Personal Ledger context). Every User is the sole owner of their own logical "Tenant" (The Ledger). Isolation is enforced at the `userId` level.
- **Credential**: A secret (Password, Token, OAuth ID) used to prove identity.
- **Provider**: The authority that validates a credential (e.g., LOCAL, GOOGLE, APPLE).
- **Session**: A temporary authorized state for a User.
- **Access Token**: A signed proof of identity. It must contain `userId`.

## Scaling Terms
- **Tenant Isolation**: The strict separation of data where one User cannot access another's data, enforced via the `tenantId` (which maps to the `userId`).
- **Horizontal Scaling**: Achieved at the infrastructure level via database read replicas, connection pooling, and application-level caching. Not exposed as a domain concern.
