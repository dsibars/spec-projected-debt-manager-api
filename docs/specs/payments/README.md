# Module: Payments

## Purpose
Manages payment records and their application to debts.

## Bounded Context
This module owns:
- Payment creation
- Payment-to-debt linking

## Dependencies
- [[specs/debts/models/Debt]] (read-only reference)
- @shared/skills/persistence/postgresql

## Entry Points
- REST: [[presentation/rest/api]]
- Commands: [[commands/]]
- Queries: [[queries/]]

## Events Produced
- [[events/PaymentApplied]]

## Events Consumed
- [[specs/debts/events/DebtRegistered]]
- [[specs/debts/events/DebtSettled]]
