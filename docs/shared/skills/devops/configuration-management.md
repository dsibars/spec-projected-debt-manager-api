# Skill: DevOps - Configuration Management

This skill defines the environment-based configuration management and the "Configuration Contract" for all shared skills.

## 1. The Configuration Contract
Every Shared Skill that requires external settings MUST define a **Configuration Contract**. This identifies the keys it consumes.

## 2. Global Key Registry (The Source of Truth)

| Category | Spec Key | Environment Variable | Default (Local) |
| :--- | :--- | :--- | :--- |
| **Server** | `server.port` | `SPD_SERVER_PORT` | `8080` |
| **Write DB** | `db.write.url` | `SPD_DB_WRITE_URL` | `jdbc:postgresql://localhost:5432/spd_write` |
| **Read DB** | `db.read.url` | `SPD_DB_READ_URL` | `jdbc:postgresql://localhost:5433/spd_read` |
| **Broker** | `messaging.url` | `SPD_BROKER_URL` | `amqp://guest:guest@localhost:5672` |

## 3. Secret Injection & Security Law
- **No Secrets in Specs**: Specifications MUST NOT contain actual passwords or keys.
- **Environment Overrides**: All configurations MUST support overrides via Environment Variables using the `SPD_` prefix.
- **Production Secrets**: In `prod`, the application MUST NOT start if mandatory secrets (e.g., `SPD_JWT_SECRET`) are missing. 
- **Secret Stores**: For production deployments, the use of a secure vault (e.g., HashiCorp Vault, AWS Secrets Manager) is MANDATORY. The application SHOULD be configured to fetch secrets at startup or via sidecar injection.

## 4. Profile Generation Law
The Builder MUST generate:
- `application.yml`: Skeleton and defaults.
- `application-local.yml`: Dev-ready connection strings.
- `application-test.yml`: Mock/InMemory overrides for CI.
