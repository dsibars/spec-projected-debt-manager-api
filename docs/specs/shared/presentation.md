# Shared Presentation: API Standards

This document defines the global HTTP and JSON standards for the Debt Manager API. All module-specific presentation specs inherit these rules.

## JSON Envelopes

To maintain consistency for API consumers, all responses must follow these structural rules.

### Success Response
All successful responses must wrap the result in a `data` field.
```json
{
  "data": { ... }
}
```
*For list operations, `data` is an array.*

### Error Response
All error responses must use a 4xx or 5xx status code and return a standardized error object.
```json
{
  "error": {
    "code": "DomainErrorCode",
    "message": "Human-readable explanation of the error."
  }
}
```

## Pagination

Any use case that returns a list of items should support the following query parameters:
- `page`: The page number (default: 1).
- `size`: The number of items per page (default: 20, max: 100).

Paginated responses should include a `meta` field:
```json
{
  "data": [...],
  "meta": {
    "totalItems": 150,
    "totalPages": 8,
    "currentPage": 1,
    "pageSize": 20
  }
}
```

## HTTP Methods

- `GET`: Retrieve data (Idempotent).
- `POST`: Create new resources.
- `PUT`: Update existing resources (Full update).
- `PATCH`: Partial update (Optional).
- `DELETE`: Remove or Archive resources.
