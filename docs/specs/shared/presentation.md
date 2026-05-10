# Shared Presentation: API Standards

This document defines the global HTTP and JSON standards for the Debt Manager API. All module-specific presentation specs inherit these rules.

## JSON Envelopes

To maintain consistency for API consumers, all responses must follow these structural rules.

### Success & Error Responses
All endpoints MUST adhere to the standard envelope schemas (e.g., `data` and `error` wrappers) defined entirely within the `@shared/skills/presentation/rest-api` technical skill.

## Pagination

Any use case that returns a list of items should support standard pagination query parameters (`page`, `size`) and respond with the standard pagination `meta` envelope as dictated by the `@shared/skills/presentation/rest-api` skill.
## API Documentation

Every implementation MUST provide automated API documentation using the `@shared/skills/presentation/openapi` skill. This documentation must be accessible at `/api/docs/ui` and serve as the contract for frontend and third-party integrations.

## HTTP Methods

- `GET`: Retrieve data (Idempotent).
- `POST`: Create new resources.
- `PUT`: Update existing resources (Full update).
- `PATCH`: Partial update (Optional).
- `DELETE`: Remove or Archive resources.
