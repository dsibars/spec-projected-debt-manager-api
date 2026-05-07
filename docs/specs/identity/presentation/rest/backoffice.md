# Presentation: Identity Backoffice REST API

## Base Path: `/api/v1/backoffice/identity`

### Create Tenant
- **Adapts**: [[../../commands/CreateTenant]]
- **Method**: `POST`
- **Path**: `/tenants`
- **Request Body**:
  ```json
  {
    "name": "string",
    "capacity": 10000,
    "region": "us-east-1"
  }
  ```
- **Success Response**: `201 Created` with `tenantId`.

### Rebalance User
- **Adapts**: [[../../commands/RebalanceUser]]
- **Method**: `POST`
- **Path**: `/users/{userId}/rebalance`
- **Request Body**:
  ```json
  {
    "targetTenantId": "uuid"
  }
  ```
- **Success Response**: `200 OK`.

### List Tenants (Query)
- **Method**: `GET`
- **Path**: `/tenants`
- **Success Response**: `200 OK` with list of Tenants (Paginated).
