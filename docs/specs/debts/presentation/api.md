# Presentation: Debts API

Interface for managing financial obligations.

> [!IMPORTANT]
> All endpoints follow [[specs/shared/presentation]].

## Base Path: `/api/v1/debts`

### Create Debt
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
- **Success Response**: `201 Created` with [[models/Debt]] in `data`.

### List Debts
- **Method**: `GET`
- **Path**: `/`
- **Query Parameters**:
  - `personId`: `uuid?`
  - `isSettled`: `boolean?`
  - `direction`: `string?`
  - `page`, `size` (standard pagination)
- **Success Response**: `200 OK` with array of [[models/Debt]] in `data` and pagination in `meta`.

### Get Debt Details
- **Method**: `GET`
- **Path**: `/{id}`
- **Success Response**: `200 OK` with [[models/Debt]] in `data`.
