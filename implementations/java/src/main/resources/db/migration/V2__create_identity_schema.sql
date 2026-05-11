-- Identity Module Schema

CREATE TABLE shards (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    capacity INTEGER NOT NULL,
    current_load INTEGER NOT NULL DEFAULT 0,
    region VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    shard_id UUID NOT NULL REFERENCES shards(id),
    email VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    last_login_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE credentials (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    provider VARCHAR(50) NOT NULL,
    secret VARCHAR(255) NOT NULL,
    provider_data TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    version INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE user_index (
    email VARCHAR(255) PRIMARY KEY,
    user_id UUID NOT NULL,
    shard_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_users_shard_id ON users(shard_id);
CREATE INDEX idx_credentials_user_id ON credentials(user_id);
