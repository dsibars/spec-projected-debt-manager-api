# Use Case: List Payments

## Goal
Retrieve a paginated list of payments, optionally filtered by debt.

## Flow
1. Receive optional filter `debtId`.
2. Receive pagination parameters `page` and `size`.
3. Query the Store for [[models/Payment]] entries matching the filter.
4. Apply sorting by `paidAt` descending.
5. Apply pagination (skip and take).
6. Return the paginated list and metadata.

## Result
- Standard paginated response (as per [[specs/shared/presentation]]).
