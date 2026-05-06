# Use Case: List People

## Goal
Retrieve a collection of all registered persons, supporting filtering and pagination.

## Flow
1. Receive optional filter `includeArchived` (default: `false`).
2. Receive optional pagination parameters `page` and `size` (defaults as per [[specs/shared/presentation]]).
3. Query the Store for all [[models/Person]] entries.
4. If `includeArchived` is `false`, exclude entries where `isArchived` is `true`.
5. Sort the remaining entries by `name` alphabetically.
6. Calculate the total count of filtered entries.
7. Apply pagination (skip and take) based on `page` and `size`.
8. Return the paginated list of [[models/Person]] entries and the pagination metadata.

## Result
- A list of [[models/Person]] objects.
- Pagination metadata (total items, total pages, etc.).
