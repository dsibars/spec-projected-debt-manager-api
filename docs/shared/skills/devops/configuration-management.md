# Skill: DevOps - Configuration Management

## Category: devops
## Provides:
- Configuration Management
## Conflicts With:
- None
## Depends On:
- @shared/skills/devops/local-development

This skill defines the environment-based configuration management and the "Configuration Contract" for all shared skills.

## 1. The Configuration Contract
Every Shared Skill that requires external settings MUST define a **Configuration Contract**. This identifies the keys it consumes.

## 2. Global Key Registry (The Source of Truth)

| Category | Spec Key | Environment Variable | Default (Local) |
| :--- | :--- | :--- | :--- |
| **Server** | `server.port` | `SPD_SERVER_PORT` | `8080` |
| **Database** | `db.primary.url` | `SPD_DB_PRIMARY_URL` | `jdbc:postgresql://localhost:5432/spd_db` |
| **Broker** | `messaging.url` | `SPD_BROKER_URL` | `amqp://guest:guest@localhost:5672` |
| **Cache** | `cache.redis.url` | `SPD_CACHE_REDIS_URL` | `redis://localhost:6379` |
| **Observability** | `otel.exporter.url` | `SPD_OTEL_EXPORTER_URL` | `http://localhost:4317` |

## 3. Profile-Based Configuration

The Builder MUST generate environment-specific configuration files:

- **`application.yml`** (or `config.yml` / `.env`): Skeleton with defaults and shared settings.
- **`application-local.yml`**: Dev-ready connection strings pointing to Docker Compose services on `localhost`.
- **`application-test.yml`**: In-memory or Testcontainer overrides for CI/test execution.
- **`application-prod.yml`**: Production settings (secrets injected via environment variables).

## 4. Secret Injection & Security Law
- **No Secrets in Specs**: Specifications MUST NOT contain actual passwords or keys.
- **Environment Overrides**: All configurations MUST support overrides via Environment Variables using the `SPD_` prefix.
- **Production Secrets**: In `prod`, the application MUST NOT start if mandatory secrets (e.g., `SPD_JWT_SECRET`) are missing.
- **Secret Stores**: For production deployments, the use of a secure vault (e.g., HashiCorp Vault, AWS Secrets Manager) is MANDATORY. The application SHOULD be configured to fetch secrets at startup or via sidecar injection.

## 5. Local Development `.env` File

The Builder MUST generate a `.env` file in the implementation root for Docker Compose:

```bash
SPD_SERVER_PORT=8080
SPD_DB_PRIMARY_URL=postgresql://spd_user:spd_pass@postgres:5432/spd_db
SPD_BROKER_URL=amqp://guest:guest@rabbitmq:5672
SPD_CACHE_REDIS_URL=redis://redis:6379
SPD_OTEL_EXPORTER_URL=http://signoz-collector:4317
```

This file is used by `docker-compose.yml` and MAY be sourced by the Makefile.
