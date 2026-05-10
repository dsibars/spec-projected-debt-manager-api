# Module: Identity

## Purpose
Manages user authentication, authorization, and account lifecycle.

## Bounded Context
This module owns:
- User registration and authentication
- JWT token management
- Account purge / GDPR deletion

## Dependencies
- @shared/skills/security/jwt
- @shared/skills/security/hashing
- @shared/skills/security/oauth-integration

## Entry Points
- REST: [[presentation/rest/api]]
- Commands: (See commands/)
- Queries: (See queries/)

## Events Produced
- [[events/UserRegistered]]
- [[events/IdentityPurgeRequested]]

## Events Consumed
- (None)
