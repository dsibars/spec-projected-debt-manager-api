# Skill: SigNoz Observability Stack

## Category: observability
## Provides:
- Signoz
## Conflicts With:
- opentelemetry
## Depends On:
- None explicitly declared


This skill defines the deployment and integration with the SigNoz observability platform.

## Architecture
- **Collector**: Every module sends telemetry to the **OpenTelemetry Collector** via OTLP.
- **Backend**: SigNoz uses **ClickHouse** as the storage engine for high-performance telemetry analysis.
- **Frontend**: A modern UI accessible at `http://localhost:3301` (default).

## Local Development (Docker)
The local `infra-up` command MUST provision:
1.  `signoz-otel-collector`: The ingestion hub.
2.  `signoz-query-service`: The API for the UI.
3.  `signoz-frontend`: The dashboard.
4.  `clickhouse`: The data store.

## Dashboards & Alerts
- Standard APM dashboards should be automatically populated by SigNoz.
- Custom business metrics (e.g., `total_debts_created`, `payment_processing_latency`) should be visualized via SigNoz Dashboards.