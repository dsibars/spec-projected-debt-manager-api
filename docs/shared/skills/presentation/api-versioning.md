# Skill: API Versioning & Lifecycle

## Category: presentation
## Provides:
- Api Versioning
## Conflicts With:
- None
## Depends On:
- @shared/skills/presentation/rest-api

This skill defines how the REST API MUST be versioned to support long-term SaaS evolution without breaking existing consumers.

## Versioning Strategy

### URL Path Versioning (Mandatory)
- All API routes MUST include a major version prefix: `/api/v{N}/`.
- Example: `/api/v1/debts`, `/api/v2/debts`.
- The version applies to the entire API surface, not individual endpoints.

### Version Format
- Major versions: `v1`, `v2`, etc.
- Minor changes (new fields, new endpoints) do NOT require a new version.
- Breaking changes (removed fields, changed behavior, new required params) REQUIRE a new major version.

### Deprecation Policy
1. When a new major version is released, the previous version MUST remain operational for at least **12 months**.
2. Deprecated versions MUST return a `Deprecation` header:
   - `Deprecation: true`
   - `Sunset: [RFC-7231 date]` indicating the final shutdown date.
3. Responses from deprecated versions SHOULD include a `Link` header pointing to the new version documentation.

### Header Negotiation (Optional Enhancement)
- Clients MAY request a specific version via the `Accept-Version: v1` header.
- If the header is absent, the latest stable version is served.
- If the requested version is unsupported, return `406 Not Acceptable`.

## Breaking Changes Definition
A change is breaking if it:
- Removes a field from a response payload.
- Changes the type of an existing field.
- Makes a previously optional field required.
- Removes an endpoint or HTTP method.
- Changes the semantics of an endpoint (e.g., `DELETE` now hard-deletes instead of archiving).

## Implementation Requirements
- The Builder MUST generate route definitions that include the version prefix.
- OpenAPI documentation MUST be generated per version (`/api/docs/ui/v1`, `/api/docs/ui/v2`).
- Controllers SHOULD be versioned (e.g., `DebtControllerV1`, `DebtControllerV2`) to avoid code duplication while supporting multiple versions.
