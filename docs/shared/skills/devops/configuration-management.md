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
| **Database** | `db.primary.url` | `SPD_DB_PRIMARY_URL` | `postgresql://localhost:5432/spd_db` |
| **Broker** | `messaging.url` | `SPD_BROKER_URL` | `amqp://guest:guest@localhost:5672` |
| **Cache** | `cache.redis.url` | `SPD_CACHE_REDIS_URL` | `redis://localhost:6379` |
| **Observability** | `otel.exporter.url` | `SPD_OTEL_EXPORTER_URL` | `http://localhost:4317` |

## 3. Profile-Based Configuration

The Builder MUST generate environment-specific configuration files. The file format and naming convention depends on the target stack, but the semantic profiles are universal:

- **`default`** (or `application.yml` / `config.toml` / `.env`): Skeleton with defaults and shared settings.
- **`local`**: Dev-ready connection strings pointing to Docker Compose services on `localhost`.
- **`test`**: In-memory or Testcontainer overrides for CI/test execution.
- **`prod`**: Production settings (secrets injected via environment variables).

#### Per-Stack Configuration Files
| Stack | Default File | Local File | Test File | Prod File |
|---|---|---|---|---|
| Java / Spring | `application.yml` | `application-local.yml` | `application-test.yml` | `application-prod.yml` |
| Kotlin / Ktor | `application.conf` (HOCON) | `application-local.conf` | `application-test.conf` | `application-prod.conf` |
| Go / Gin | `.env` | `.env.local` | `.env.test` | `.env.prod` |
| Rust / Axum | `config/default.toml` | `config/local.toml` | `config/test.toml` | `config/prod.toml` |

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
