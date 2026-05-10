# Module: Debts

## Purpose
Manages financial obligations between users and other people.

## Bounded Context
This module owns:
- Debt lifecycle (create, update, settle, archive)
- Payment tracking
- Debt summary projections

## Dependencies
- [[specs/people/models/Person]] (read-only identity reference)
- [[specs/identity/models/User]] (tenant isolation)
- @shared/skills/persistence/postgresql
- @shared/skills/messaging/rabbitmq
- @shared/skills/security/jwt

## Entry Points
- REST: [[presentation/rest/api]], [[presentation/rest/payments]]
- Subscribers: (See presentation/subscribers/)
- Commands: (See commands/)
- Queries: (See queries/)

## Events Produced
- [[events/DebtRegistered]]
- [[events/DebtSettled]]

## Events Consumed
- (None from payments, payments are internal)
- [[specs/people/events/PersonCreated]]
- [[specs/people/events/PersonUpdated]]
- [[specs/identity/events/IdentityPurgeRequested]]
