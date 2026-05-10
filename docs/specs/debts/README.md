# Module: Debts

## Purpose
Manages financial obligations between users and other people.

## Bounded Context
This module owns:
- Debt lifecycle (create, update, settle, archive)
- Payment tracking (via events from Payments module)
- Debt summary projections

## Dependencies
- [[specs/people/models/Person]] (read-only identity reference)
- [[specs/identity/models/User]] (tenant isolation)
- @shared/skills/persistence/postgresql
- @shared/skills/messaging/rabbitmq
- @shared/skills/security/jwt

## Entry Points
- REST: [[presentation/rest/api]]
- Subscribers: [[presentation/subscribers/]]
- Commands: [[commands/]]
- Queries: [[queries/]]

## Events Produced
- [[events/DebtRegistered]]
- [[events/DebtSettled]]

## Events Consumed
- [[specs/payments/events/PaymentApplied]]
- [[specs/people/events/PersonCreated]]
- [[specs/people/events/PersonUpdated]]
- [[specs/identity/events/IdentityPurgeRequested]]
