# Definitions: Identity Domain

This document defines the core concepts of Identity and Access Management (IAM) and Multi-Tenancy.

## Core Terms
- **User**: The technical identity authorized to access the system. A User belongs to exactly one Tenant for data isolation.
- **Tenant**: A logical partition of data. It acts as the primary boundary for horizontal scaling and data isolation. All business data (Debts, People, etc.) is scoped to a Tenant.
- **Credential**: A secret (Password, Token, OAuth ID) used to prove identity.
- **Provider**: The authority that validates a credential (e.g., LOCAL, GOOGLE, APPLE).
- **Session**: A temporary authorized state for a User.
- **Access Token**: A cryptographically signed proof of an active session (e.g., JWT). It must contain both `userId` and `tenantId`.
- **Refresh Token**: A long-lived credential used to obtain new Access Tokens.
- **MFA**: Multi-Factor Authentication (e.g., TOTP, SMS).

## Multi-Tenancy Terms
- **Tenant Assignment**: The process of assigning a new User to a Tenant based on load balancing, geographic requirements, or shard capacity.
- **Rebalancing**: An administrative operation to move a User (and all their associated data) from one Tenant to another to optimize system performance or resource utilization.
- **Isolation Boundary**: The strict separation of data where one Tenant cannot access another's data, enforced at the application and persistence layers via the `tenantId`.
