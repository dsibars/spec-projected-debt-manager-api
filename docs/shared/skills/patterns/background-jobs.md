# Skill: Background Jobs & Async Processing

## Category: patterns
## Provides:
- Background Jobs
## Conflicts With:
- None
## Depends On:
- @shared/skills/messaging/rabbitmq

This skill defines how long-running, asynchronous, or deferrable work MUST be handled in a top-tier SaaS architecture.

## Principles
1.  **Never Block the User**: Operations that exceed 500ms MUST NOT be executed synchronously in the HTTP request thread.
2.  **At-Least-Once Delivery**: Jobs MUST be designed to handle duplicate execution gracefully (idempotency).
3.  **Observability**: Every job MUST emit structured logs and traces with correlation IDs.

## When to Use Background Jobs
- **Report Generation**: CSV/PDF exports, analytics summaries.
- **Bulk Operations**: Importing hundreds of records, bulk updates.
- **Notifications**: Email, SMS, push notifications.
- **Data Archival**: Moving old records to cold storage.
- **Cross-Module Sync**: Large backfills, data migrations.
- **Third-Party Integrations**: Calling external APIs with variable latency.

## Job Structure
Every job MUST define:
- `jobId`: UUID (for tracing and deduplication).
- `tenantId`: UUID (for isolation and routing).
- `payload`: Serializable input data.
- `maxRetries`: Integer (default: 3).
- `retryDelay`: Duration (exponential backoff: 1s, 2s, 4s).

## Job Queue Implementation
- **Broker**: Use the same message broker as domain events (RabbitMQ) with a dedicated `jobs` exchange.
- **Queue Naming**: `jobs.{module}.{jobType}` (e.g., `jobs.debts.export`).
- **Worker Processes**: Separate worker binaries (or threads) consume from job queues.
- **Dead Letter Queue**: Failed jobs after max retries are moved to `jobs.{module}.{jobType}.dlq` for manual inspection.

## Error Handling
- **Retryable Errors** (network timeouts, DB lock): Retry with backoff.
- **Permanent Errors** (invalid data, business rule violation): Move to DLQ immediately.
- **Poison Pills**: If a job fails deterministically on every retry, it MUST be quarantined.

## Progress Tracking (Optional)
For long-running jobs (e.g., bulk imports):
- Store job status in Redis or a dedicated `job_status` table.
- Statuses: `PENDING`, `RUNNING`, `COMPLETED`, `FAILED`.
- Expose a query endpoint: `GET /api/v1/jobs/{jobId}/status`.

## Configuration Contract
- **Job Concurrency**: `jobs.concurrency` (default: 10 workers per queue).
- **Job Timeout**: `jobs.timeout-seconds` (default: 300).
- **Retention**: `jobs.retention-days` (default: 7 days for completed jobs).
