# Module: People

## Purpose
Manages the directory of individuals a user interacts with financially.

## Bounded Context
This module owns:
- Person profiles
- Contact information
- Soft deletion / archival

## Dependencies
- [[specs/identity/models/User]] (tenant isolation)
- @shared/skills/persistence/postgresql
- @shared/skills/security/pii-management

## Entry Points
- REST: [[presentation/rest/api]]
- Commands: (See commands/)
- Queries: (See queries/)

## Events Produced
- [[events/PersonCreated]]
- [[events/PersonUpdated]]
- [[events/PeoplePurged]]
- [[specs/shared/events/ModuleDataPurged]]

## Events Consumed
- [[specs/identity/events/IdentityPurgeRequested]]
