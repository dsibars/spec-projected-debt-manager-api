# Presentation: Payments API

Standard REST interface for managing payments.

> [!IMPORTANT]
> All endpoints follow the shared standards in [[specs/shared/presentation]].

## Base Path: `/api/v1/payments`

### Register Payment
- **Adapts**: [[../../commands/RegisterPayment]]
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

### Get Payment Details
- **Adapts**: [[../../queries/GetPayment]]
- **Method**: `GET`
- **Path**: `/{id}`
- **Success Response**: `200 OK` with [[../../models/Payment]] in `data`.

### List Payments
- **Adapts**: [[../../queries/ListPayments]]
- **Method**: `GET`
- **Path**: `/`
- **Query Parameters**:
  - `debtId`: `uuid?`
  - `page`, `size` (pagination)
- **Success Response**: `200 OK` with paginated array of [[../../models/Payment]].
