# Definitions: Identity Domain

This document defines the core concepts of Identity, Access Management (IAM), and Horizontal Sharding.

## Core Terms
- **User**: The technical identity authorized to access the system. A User belongs to exactly one **Shard** for physical data residency.
- **Shard**: A physical partition of data. It acts as the primary unit for horizontal scaling. Shards are collections of Users.
- **Tenant**: (Synonym for User in this Personal Ledger context). Every User is the sole owner of their own logical "Tenant" (The Ledger). Isolation is enforced at the `userId` level.
- **Credential**: A secret (Password, Token, OAuth ID) used to prove identity.
- **Provider**: The authority that validates a credential (e.g., LOCAL, GOOGLE, APPLE).
- **Session**: A temporary authorized state for a User.
- **Access Token**: A signed proof of identity. It must contain `userId` (for isolation) and `shardId` (for routing).

## Scaling & Sharding Terms
- **Shard Assignment**: The process of assigning a new User to a Shard based on load or geography.
- **Rebalancing**: An administrative operation to move a User (and their isolated data) from one Shard to another.
- **Isolation Boundary**: The strict separation of data where one User cannot access another's data, enforced via the `tenantId` (which maps to the `userId`).
