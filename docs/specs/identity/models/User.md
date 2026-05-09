# Model: User Aggregate

## Goal
To represent a unique identity within the system.

## Properties
- `id`: `uuid` (Primary Key, global `userId`)
- `shardId`: `uuid` (Foreign Key to [[Shard]], mandatory for horizontal scaling)
- `email`: `string` (Unique)
- `isActive`: `boolean` (Default: `true`)
- `lastLoginAt`: `datetime?`
- `createdAt`: `datetime`
- `updatedAt`: `datetime`

