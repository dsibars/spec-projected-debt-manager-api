# Behavior: Identity Management

## Registration
**Given** a guest user
**When** they register with a valid email and password
**Then** a new User is created
**And** the User is assigned to the active Tenant with the lowest current load
**And** a `UserRegistered` event is emitted

## Authentication
**Given** an existing user
**When** they login with correct credentials
**Then** a valid JWT token is returned
**And** the token contains `sub` (userId) and `tid` (tenantId) claims
