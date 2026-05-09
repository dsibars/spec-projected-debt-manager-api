# Skill: Presentation - REST API

This skill defines the technical laws for projecting a RESTful HTTP server.

## Technical Requirements

*   **Transport Mechanism**: HTTP/1.1 or HTTP/2.
*   **Content Type**: All requests and responses must consume and produce `application/json; charset=utf-8`.
*   **Response Enveloping**: 
    *   All successful responses MUST be wrapped in a `data` field.
    *   List responses MUST include a `meta` field for pagination (if applicable).
    *   Standard format: `{"data": ..., "meta": {...}}`.
*   **Error Handling (Global Interceptor)**: 
    *   The implementation MUST use a Global Exception Handler mechanism (e.g., Spring `@ControllerAdvice`, Gin Middleware) to catch all `DomainException` occurrences.
    *   The handler MUST translate these domain errors into the standard JSON Error Envelope: `{"error": {"code": "...", "message": "..."}}`.
*   **HTTP Status Codes**:
    *   `200 OK`: Successful Queries or Updates.
    *   `201 Created`: Successful Commands that create a resource.
    *   `204 No Content`: Successful Deletions/Archivals.
    *   `400 Bad Request`: Domain validation failures (e.g., `InvalidAmount`).
    *   `401 Unauthorized`: Authentication failures (Missing/Invalid JWT).
    *   `403 Forbidden`: Authorization failures (Tenant mismatch).
    *   `404 Not Found`: Entity not found failures (e.g., `DebtNotFound`).
    *   `409 Conflict`: Business rule conflicts (e.g., `DebtSettled`).
    *   `422 Unprocessable Entity`: Synthetic validation failures (Schema/Type errors).
    *   `500 Internal Server Error`: Unhandled technical exceptions.
*   **Pagination Implementation**:
    *   The API MUST support `page` (default 1) and `size` (default 20) query parameters for all list operations.
    *   The `meta` object MUST contain: `totalItems`, `totalPages`, `currentPage`, and `pageSize`.
*   **Routing Definition**: Route paths must strictly map to the definitions provided in `docs/specs/[module]/presentation/rest/`.

## API Documentation
- Every implementation MUST provide automated OpenAPI v3 documentation.
- The UI MUST be served at `/api/docs/ui`.
