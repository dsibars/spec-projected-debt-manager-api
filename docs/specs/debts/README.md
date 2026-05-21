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
- [[events/PaymentRegistered]]
- [[events/DebtUpdated]]
- [[events/DebtDeleted]]
- [[events/DebtsPurged]]
- [[events/RequestPersonBackfill]]

## Events Consumed
- [[specs/people/events/PersonCreated]]
- [[specs/people/events/PersonUpdated]]
- [[specs/people/events/PersonArchived]]
- [[specs/identity/events/IdentityPurgeRequested]]
- debts.DebtRegistered (internal, for projection update)
- debts.DebtUpdated (internal, for projection update)
- debts.PaymentRegistered (internal, for projection update)
- debts.DebtDeleted (internal, for projection update)
