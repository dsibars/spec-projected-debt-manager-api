# Presentation: People API

This document defines the RESTful interface for managing people.

> [!IMPORTANT]
> All endpoints follow the global JSON envelopes and pagination standards defined in [[specs/shared/presentation]].

## Base Path: `/api/v1/people`

### Create Person
- **Adapts**: [[commands/CreatePerson]]
- **Method**: `POST`
- **Path**: `/`
- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string?",
    "phone": "string?"
  }
  ```
- **Success Response**: `201 Created` with the generated `id` wrapped in the standard `data` envelope.
- **Error Responses**:
  - `400 Bad Request`: `InvalidName`, `InvalidEmailFormat`.
  - `409 Conflict`: `DuplicateEmail`.

### Get Person
- **Adapts**: [[queries/GetPerson]]
- **Method**: `GET`
- **Path**: `/{id}`
- **Success Response**: `200 OK` with [[models/Person]] wrapped in the standard `data` envelope.
- **Error Responses**:
  - `404 Not Found`: `PersonNotFound`.

### List People
- **Adapts**: [[queries/ListPeople]]
- **Method**: `GET`
- **Path**: `/`
- **Query Parameters**:
  - `includeArchived`: `boolean` (default: `false`)
  - `page`: `number` (pagination)
  - `size`: `number` (pagination)
- **Success Response**: `200 OK` with a JSON Array of [[models/Person]] in the `data` envelope and pagination info in the `meta` envelope.

### Update Person
- **Adapts**: [[commands/UpdatePerson]]
- **Method**: `PUT`
- **Path**: `/{id}`
- **Request Body**:
  ```json
  {
    "name": "string?",
    "email": "string?",
    "phone": "string?"
  }
  ```
- **Success Response**: `200 OK` with the `id` in the `data` envelope.

### Delete Person (Archive)
- **Adapts**: [[commands/DeletePerson]]
- **Method**: `DELETE`
- **Path**: `/{id}`
- **Success Response**: `204 No Content`.
