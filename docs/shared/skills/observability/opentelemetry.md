# Skill: OpenTelemetry & Observability

This skill defines the law for instrumenting the system with traces, metrics, and logs.

## Principles
1.  **Vendor Agnostic**: Use the OpenTelemetry SDK/API exclusively.
2.  **Context Propagation**: Propagate `traceId` and `spanId` across PROCESS and MESSAGE boundaries.
3.  **Semantic Conventions**: Follow OTel naming standards.

## Logging Law (Structured Logging)
All applications MUST produce logs in **Structured JSON** format to facilitate automated ingestion.
- **Mandatory Fields**: `timestamp`, `level`, `traceId`, `spanId`, `tenantId`, `message`.
- **Log Levels**:
    - `INFO`: Business milestones (e.g., "User Registered", "Debt Created").
    - `WARN`: Recoverable errors (e.g., "Shard in Maintenance - retrying").
    - `ERROR`: System failures or unhandled exceptions.
- **PII Protection**: Never log sensitive data (passwords, PII) in plain text.

## Instrumentation Points
- **REST Adapters**: Start/Continue root spans.
- **Application Layer**: Wrap Command/Query handlers in child spans.
- **Outbound Ports**: Instrument all Repository and External API calls.
- **Messaging**: Carry trace context in `EventEnvelope` metadata.

## Configuration Contract
- **Exporter URL**: `otel.exporter.url`
- **Service Name**: `otel.service.name`
