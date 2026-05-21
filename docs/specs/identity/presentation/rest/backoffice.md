# Presentation: Identity Backoffice REST API

## Base Path: `/api/v1/backoffice/identity`

### Delete User
- **Adapts**: [[commands/DeleteUser]]
- **Method**: `DELETE`
- **Path**: `/users/{userId}`
- **Success Response**: `204 No Content`.

### List Users (Query)
- **Method**: `GET`
- **Path**: `/users`
- **Success Response**: `200 OK` with list of Users (Paginated).
