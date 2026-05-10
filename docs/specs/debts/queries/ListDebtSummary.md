# Query: List Debt Summary

## Goal
Retrieve the paginated, denormalized dashboard view.

## Flow
1. Receive optional filters: `personId`, `isSettled`.
2. Query the [[projections/DebtSummaryProjection]] read model.
3. Apply standard pagination.
4. Return list of [[projections/DebtSummaryProjection]].
