# Skill: OpenTelemetry Instrumentation

This skill defines the law for instrumenting the system with traces, metrics, and logs.

## Principles
1.  **Vendor Agnostic**: All instrumentation MUST use the OpenTelemetry SDK/API.
2.  **Context Propagation**: The `traceId` and `spanId` MUST be propagated across process boundaries (e.g., via HTTP headers or Message Metadata).
3.  **Automatic Instrumentation**: Use library-specific auto-instrumentation (e.g., Spring Boot Starter, Go Middleware) wherever possible.
4.  **Semantic Conventions**: Follow the [OTel Semantic Conventions](https://opentelemetry.io/docs/concepts/semantic-conventions/) for span and attribute naming.

## Instrumentation Points
- **REST Adapters**: Every incoming request must start a new root span or continue an existing one.
- **Application Layer**: Every Command/Query handler must be wrapped in a child span named after the use case.
- **Outbound Ports**: Every Repository and External API call must be instrumented.
- **Messaging**: Every event published to the Broker must carry the current trace context.

## Configuration Contract
This skill consumes the following keys from `@shared/skills/devops/configuration-management`:
- **Exporter URL**: `otel.exporter.url` (Default: `http://localhost:4317`)
- **Service Name**: `otel.service.name` (Injected by Builder)

## Technical Requirements
- **Protocol**: OTLP/gRPC.
- **Resource Attributes**: Every span must include `service.name`, `service.version`, and `deployment.environment`.
