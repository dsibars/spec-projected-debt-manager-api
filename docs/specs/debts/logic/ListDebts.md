# Use Case: List Debts

## Goal
Retrieve a paginated list of debts, with optional filtering.

## Flow
1. Receive optional filters: `personId`, `isSettled`, `direction`.
2. Receive pagination parameters `page` and `size`.
3. Query Store for [[models/Debt]] entries matching filters.
4. Calculate total matches and pages.
5. Apply pagination (skip/take).
6. Return list of [[models/Debt]] and pagination metadata.

## Result
- Standard paginated response (as per [[specs/shared/presentation]]).
