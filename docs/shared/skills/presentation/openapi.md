# Skill: API Documentation - OpenAPI (Swagger)

## Category: presentation
## Provides:
- Openapi
## Conflicts With:
- rest-api
## Depends On:
- None explicitly declared


This skill defines the requirements for automated API documentation and discovery.

## 1. Core Principles
- **Self-Documenting Code**: The API documentation must be automatically generated from the source code and specifications.
- **Interactive Exploration**: Every implementation MUST provide a web-based UI (e.g., Swagger UI) to allow developers to explore and test the API endpoints.
- **Contract-First Alignment**: The generated documentation MUST strictly match the paths, methods, and schemas defined in the `docs/specs/[module]/presentation/rest/` specifications.

## 2. Technical Requirements
- **Specification Version**: OpenAPI 3.0 or 3.1.
- **Authentication**: The UI must support JWT Bearer authentication. It must allow users to input a token that will be included in the `Authorization` header for all requests.
- **Endpoint Discovery**: All Command and Query endpoints defined in the module's REST presentation MUST be included.
- **Schema Definitions**: All request and response objects (including success and error envelopes defined in `@shared/specs/presentation`) MUST be documented with clear types and constraints.

## 3. Standard Paths
To ensure consistency across implementations, the following paths are mandated:
- **OpenAPI Spec (JSON/YAML)**: `/api/docs/openapi.json` or `/api/docs/openapi.yaml`
- **Swagger UI**: `/api/docs/ui`

## 4. Implementation Guidance
- **Java/Kotlin**: Use `springdoc-openapi` or `ktor-swagger`.
- **Go**: Use `swaggo/swag` with Gin middleware.
- **Rust**: Use `utoipa` with Axum integration.

## 5. Metadata
The documentation MUST include:
- **Title**: SPD Debt Manager - [Module Name] API
- **Version**: Current implementation version.
- **Description**: Projected from the module's summary.