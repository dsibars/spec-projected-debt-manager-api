# Module: Shared Kernel

## Purpose
Contains cross-cutting domain models, events, and contracts used by all business modules.

## Bounded Context
This module owns:
- Common value objects (Currency, Email)
- Event envelope standards
- Global presentation standards
- Base implementation composition rules

## Dependencies
- (None; this is the root of the dependency graph)

## Entry Points
- (None; this module has no REST API or subscribers)

## Events Produced
- [[events/ModuleDataPurged]]

## Events Consumed
- (None)
