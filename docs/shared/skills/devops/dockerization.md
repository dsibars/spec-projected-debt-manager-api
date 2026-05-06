# Skill: DevOps - Dockerization

This skill defines the standards for containerizing the application and its environment.

## Container Standards

- **Base Images**: Use official, minimal base images (e.g., `alpine`, `slim`, or `distroless`).
- **Multi-stage Builds**: Mandatory for all implementations to separate build artifacts from the runtime environment.
- **User**: Do not run applications as `root` inside the container. Create a dedicated `spd_user`.
- **Environment Variables**: Use `.env` files for local development and standard environment variables for production.

## Docker Compose (Local Development)

The system must provide a `docker-compose.yml` in each implementation directory to spin up the required infrastructure.

### Standard Services:
- `db`: PostgreSQL 16 image.
- `broker`: RabbitMQ 3-management image (with management UI enabled).
- `api`: The implementation being worked on (optional, if running via `make run` outside docker).

## Makefile Integration

The `Makefile` should orchestrate the Docker lifecycle:
- `make infra-up`: Starts infrastructure (e.g., database).
- `make infra-down`: Stops infrastructure.
- `make docker-build`: Builds the implementation's docker image.
- `make docker-run`: Runs the implementation inside a container.

## Networking
- Containers must communicate via a shared network (e.g., `spd_network`).
- Use service names as hostnames (e.g., `postgresql://db:5432`).
