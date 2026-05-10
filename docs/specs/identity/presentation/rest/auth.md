# Presentation: Identity REST API

## Base Path: `/api/v1/auth`

### Register
- **Adapts**: [[commands/RegisterUser]]
- **Method**: `POST`
- **Path**: `/register`
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string",
    "provider": "LOCAL"
  }
  ```
- **Success Response**: `201 Created` with `userId`.

### Login (Local)
- **Adapts**: [[commands/AuthenticateUser]]
- **Method**: `POST`
- **Path**: `/login`
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string",
    "provider": "LOCAL"
  }
  ```
- **Success Response**: `200 OK` with `accessToken` and `refreshToken`.

### Get Current User (Me)
- **Adapts**: [[queries/GetCurrentUser]]
- **Method**: `GET`
- **Path**: `/me`
- **Success Response**: `200 OK` with user profile in `data`.
