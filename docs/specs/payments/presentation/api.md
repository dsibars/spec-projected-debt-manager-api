# Presentation: Payments API

Standard REST interface for managing payments.

> [!IMPORTANT]
> All endpoints follow the shared standards in [[specs/shared/presentation]].

## Base Path: `/api/v1/payments`

### Register Payment
- **Method**: `POST`
- **Path**: `/`
- **Request Body**:
  ```json
  {
    "debtId": "uuid",
    "amount": "integer",
    "notes": "string?",
    "paidAt": "datetime?"
  }
  ```
- **Success Response**: `201 Created` with the generated `id` in `data`.
- **Error Responses**:
  - `400 Bad Request`: `InvalidAmount`, `PaymentExceedsBalance`.
  - `404 Not Found`: `DebtNotFound`.

### List Payments
- **Method**: `GET`
- **Path**: `/`
- **Query Parameters**:
  - `debtId`: `uuid?`
  - `page`, `size` (pagination)
- **Success Response**: `200 OK` with paginated array of [[models/Payment]].

### Get Payment Details
- **Method**: `GET`
- **Path**: `/{id}`
- **Success Response**: `200 OK` with [[models/Payment]] in `data`.
