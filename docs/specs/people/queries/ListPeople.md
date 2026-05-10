# Use Case: List People

## Goal
Retrieve a paginated list of people in the user's directory.

## Input
- `tenantId`: UUID (from Auth context)
- `page`: Integer (default: 1)
- `size`: Integer (default: 20, max: 100)
- `includeArchived`: Boolean (default: false)

## Flow
1. Validate `page` >= 1 and `size` between 1 and 100.
2. Query the Person aggregate store for records matching `tenantId`.
3. If `includeArchived` is false, filter out archived records.
4. Order by `name` ascending.
5. Calculate total matches and pages.
6. Apply pagination (skip/take).
7. Return list of [[models/Person]] and pagination metadata.

## Result
- Standard paginated response (as per [[specs/shared/presentation]]).

## Errors
- `InvalidPagination`: If `page` or `size` are out of bounds.
