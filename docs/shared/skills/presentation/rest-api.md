# Skill: Presentation - REST API

This skill defines the technical laws for projecting a RESTful HTTP server.

## Technical Requirements

*   **Transport Mechanism**: HTTP/1.1 or HTTP/2.
*   **Content Type**: All requests and responses must consume and produce `application/json; charset=utf-8`.
*   **Error Handling (Global Interceptor)**: 
    *   The implementation MUST use a Global Exception Handler mechanism (e.g., Spring `@ControllerAdvice`, Gin Middleware) to catch all `DomainException` occurrences.
    *   The handler MUST translate these domain errors into the standard JSON Error Envelope defined in `docs/specs/shared/presentation.md`.
*   **HTTP Status Codes**:
    *   `200 OK`: Successful Queries or Updates.
    *   `201 Created`: Successful Commands that create a resource.
    *   `204 No Content`: Successful Deletions/Archivals.
    *   `400 Bad Request`: Domain validation failures (e.g., `InvalidAmount`).
    *   `404 Not Found`: Entity not found failures (e.g., `DebtNotFound`).
    *   `409 Conflict`: Business rule conflicts (e.g., `DebtSettled`).
    *   `500 Internal Server Error`: Unhandled technical exceptions.
*   **Routing Definition**: Route paths must strictly map to the definitions provided in `docs/specs/[module]/presentation/api.md`.
