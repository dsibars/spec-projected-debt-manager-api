# Skill: DevOps - Dockerization

## Category: devops
## Provides:
- Dockerization
## Conflicts With:
- None
## Depends On:
- @shared/skills/devops/local-development

This skill defines the standards for containerizing the application and its local development environment.

## Container Standards

- **Base Images**: Use official, minimal base images (e.g., `alpine`, `slim`, or `distroless`).
- **Multi-stage Builds**: Mandatory for production Dockerfiles to separate build artifacts from the runtime environment.
- **User**: Do not run applications as `root` inside the container. Create a dedicated `spd_user`.
- **Environment Variables**: Use `.env` files for local development and standard environment variables for production.

## Docker Compose (Local Development)

The system must provide a `docker-compose.yml` in each implementation directory to spin up the required infrastructure.

### Standard Services:
- `postgres`: PostgreSQL 16 (primary database for aggregates and projections).
- `rabbitmq`: RabbitMQ 3-management (AMQP + management UI at port 15672).
- `redis`: Redis 7 (caching and session storage).
- `signoz-collector` (optional): OpenTelemetry ingestion hub.
- `signoz-ui` (optional): Observability dashboard at port 3301.

### Application Service:
- The application MAY run inside Docker Compose OR natively on the host connected to the Compose network.
- **Native is preferred** for compiled languages (Java, Go, Rust) to enable faster hot reload via the language's native tooling.
- If running inside Compose, mount the source directory and use the language's hot-reload mechanism.

## Makefile Integration

The `Makefile` orchestrates the Docker lifecycle:
- `make start-core`: Starts infrastructure services (Postgres, RabbitMQ, Redis).
- `make stop`: Stops application and infrastructure.
- `make docker-build`: Builds the production Docker image.
- `make docker-run`: Runs the production image locally for smoke testing.

## Networking
- Containers must communicate via a shared network (e.g., `spd_network`).
- Use service names as hostnames inside Compose (e.g., `postgresql://postgres:5432`).
- For native application + Docker infrastructure, use `localhost` (ports mapped) or join the app's container to `spd_network`.
