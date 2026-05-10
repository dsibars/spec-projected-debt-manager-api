# Presentation: Debts API

Interface for managing financial obligations.

> [!IMPORTANT]
> All endpoints follow [[specs/shared/presentation]].

## Base Path: `/api/v1/debts`

### Create Debt
- **Adapts**: [[commands/CreateDebt]]
- **Method**: `POST`
- **Path**: `/`
- **Request Body**:
  ```json
  {
    "personId": "uuid",
    "name": "string",
    "totalAmount": "integer",
    "direction": "OWED_TO_ME | I_OWE",
    "currency": "string?",
    "dueDate": "datetime?"
  }
  ```
- **Success Response**: `201 Created` with the generated `id` in `data`.

### Get Debt Details
- **Adapts**: [[queries/GetDebt]]
- **Method**: `GET`
- **Path**: `/{id}`
- **Success Response**: `200 OK` with [[models/Debt]] in `data`.

### List Debts
- **Adapts**: [[queries/ListDebts]]
- **Method**: `GET`
- **Path**: `/`
- **Query Parameters**:
  - `personId`: `uuid?`
  - `isSettled`: `boolean?`
  - `direction`: `string?`
  - `page`, `size` (standard pagination)
- **Success Response**: `200 OK` with array of [[models/Debt]] in `data` and pagination in `meta`.

### Update Debt
- **Adapts**: [[commands/UpdateDebt]]
- **Method**: `PUT`
- **Path**: `/{id}`
- **Request Body**:
  ```json
  {
    "name": "string?",
    "dueDate": "datetime?"
  }
  ```
- **Success Response**: `200 OK` with the `id` in `data`.

### Delete Debt
- **Adapts**: [[commands/DeleteDebt]]
- **Method**: `DELETE`
- **Path**: `/{id}`
- **Success Response**: `204 No Content`.
