# Subscriber: Process Person Backfill

## Goal
To adapt incoming backfill requests to the replay command.

## Subscribes To
- `debts.RequestPersonBackfill`

## Adapts
- [[../../commands/ReplayActivePeople]]

## Flow
1. Receive **`EventEnvelope`**.
2. Call [[../../commands/ReplayActivePeople]].
