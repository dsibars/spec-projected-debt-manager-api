# Presentation: Identity Backoffice REST API

## Base Path: `/api/v1/backoffice/identity`

### Create Shard
- **Adapts**: [[../../commands/CreateShard]]
- **Method**: `POST`
- **Path**: `/shards`
- **Request Body**:
  ```json
  {
    "name": "string",
    "capacity": 10000,
    "region": "us-east-1"
  }
  ```
- **Success Response**: `201 Created` with `shardId`.

### Rebalance User
- **Adapts**: [[../../commands/RebalanceUser]]
- **Method**: `POST`
- **Path**: `/users/{userId}/rebalance`
- **Request Body**:
  ```json
  {
    "targetShardId": "uuid"
  }
  ```
- **Success Response**: `200 OK`.

### Delete User
- **Adapts**: [[../../commands/DeleteUser]]
- **Method**: `DELETE`
- **Path**: `/users/{userId}`
- **Success Response**: `204 No Content`.

### List Shards (Query)
- **Method**: `GET`
- **Path**: `/shards`
- **Success Response**: `200 OK` with list of Shards (Paginated).
