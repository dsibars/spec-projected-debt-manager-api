# Skill: DevOps - Configuration Management

This skill defines the environment-based configuration management and the "Configuration Contract" for all shared skills.

## 1. The Configuration Contract
Every Shared Skill that requires external settings MUST define a **Configuration Contract**. This contract identifies the keys it consumes. The Builder uses these contracts to aggregate the final configuration files.

## 2. Global Key Registry (The Source of Truth)
To prevent naming collisions, all skills MUST use the following standardized keys:

| Category | Spec Key | Environment Variable | Default (Local) |
| :--- | :--- | :--- | :--- |
| **Server** | `server.port` | `SPD_SERVER_PORT` | `8080` |
| **Write DB** | `db.write.url` | `SPD_DB_WRITE_URL` | `jdbc:postgresql://localhost:5432/spd_write` |
| **Read DB** | `db.read.url` | `SPD_DB_READ_URL` | `jdbc:postgresql://localhost:5433/spd_read` |
| **Broker** | `messaging.url` | `SPD_BROKER_URL` | `amqp://guest:guest@localhost:5672` |
| **Security** | `security.jwt.secret` | `SPD_JWT_SECRET` | `dev-secret-key-change-me-in-prod` |
| **Telemetry** | `otel.exporter.url` | `SPD_OTEL_EXPORTER_URL` | `http://localhost:4317` |

## 3. Profile Generation Law
The Builder MUST automatically generate configuration files for each implementation target based on the skills assigned to that module:

### File: `application.yml` (Default)
Contains the "Key Skeleton" and non-sensitive defaults (ports, service names).

### File: `application-local.yml` (Local Profile)
Contains the values required to connect to the standard infrastructure defined in `dockerization.md`.

### File: `application-test.yml` (Test Profile)
Contains overrides for unit/integration testing (e.g., `db.write.url=jdbc:h2:mem:testdb`).

## 4. Secret Injection Law
- **Placeholders**: The generated `application.yml` MUST use `${KEY:DEFAULT}` format to allow environment variable overrides.
- **Production**: In `prod`, the application MUST fail fast if a required secret key (marked as `MANDATORY` in the skill) is missing.
