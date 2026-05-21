# Skill: DevOps - Health & Probes

## Category: devops
## Provides:
- Health Probes
## Conflicts With:
- cargo
- configuration-management
- dockerization
- go-modules
- maven
## Depends On:
- None explicitly declared


This skill defines the mandatory health monitoring endpoints for production readiness.

## 1. Mandatory Endpoints
The application MUST expose the following endpoints:
- `/health/live`: Indicates the application process is running (Liveness).
- `/health/ready`: Indicates the application is ready to accept traffic (Readiness).

## 2. Readiness Logic (Shard-Aware)
To be considered "Ready," the application MUST verify:
- **Write Connectivity**: A successful `SELECT 1` against the `WRITE_DB`.
- **Read Connectivity**: A successful `SELECT 1` against the `READ_DB`.
- **Broker Connectivity**: A successful heartbeat with RabbitMQ.

## 3. Telemetry Integration
- Health check failures MUST be logged as `CRITICAL` events.
- SigNoz must track the uptime and latency of these health probes.

## 4. Configuration Contract
- **Probe Frequency**: `health.probe.interval-seconds` (Default: `10`).
- **Failure Threshold**: `health.probe.failure-threshold` (Default: `3`).