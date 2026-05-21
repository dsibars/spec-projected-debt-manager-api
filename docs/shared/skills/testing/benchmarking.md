# Skill: Benchmarking

## Category: testing
## Provides:
- Benchmarking
## Conflicts With:
- behavior-projection
## Depends On:
- @shared/skills/testing/strategy
- @shared/skills/devops/local-development
- @shared/skills/messaging/rabbitmq


This skill defines a **standardized, deterministic benchmark protocol** for comparing implementations across languages, platforms, or even different architectural choices within the same stack.

The benchmark is a first-class SPD artifact. It is defined in the docs layer, projected into each implementation by the Builder, and produces a **machine-readable report** that can be diffed across targets.

---

## 1. Philosophy

- **Determinism**: The same benchmark run against two implementations MUST produce comparable results. The workload, data shapes, and execution order are fixed by the spec, not invented by the Builder.
- **Full-Stack Exercise**: A valid benchmark touches every architectural layer: HTTP API, domain logic, persistence (write + read), event publishing, event consumption, projection updates, and cross-module eventual consistency.
- **Language Agnosticism**: The benchmark scenario is described in domain terms. Each Builder projects it into the target's native benchmark harness.
- **Report Standardization**: All implementations emit the same JSON report schema. A separate comparison agent can ingest two reports and produce a human-readable analysis without understanding the underlying code.

---

## 2. The Benchmark Workload

The benchmark consists of **5 phases** executed sequentially. Each phase measures different system characteristics.

### Phase 0: Environment Reset
Before each benchmark run, the system MUST be reset to a known baseline:
1. Truncate all tenant data (preserve migrations and schema).
2. Seed exactly **1 tenant** and **1 admin user**.
3. Warm up the connection pool by executing a trivial `SELECT 1`.

### Phase 1: Write Throughput (Commands)
**Goal**: Measure aggregate creation throughput and latency.

1. Authenticate as the admin user. Record token acquisition time.
2. Create **1,000 Person** aggregates via the REST API (`POST /api/v1/people`).
   - Use deterministic fake data (Faker seed = `42`).
   - Every 10th person has an optional email; others do not.
3. Create **2,000 Debt** aggregates via the REST API (`POST /api/v1/debts`).
   - Each debt references a randomly selected person from the 1,000 created (deterministic PRNG, seed = `42`).
   - Mix of currencies (USD, EUR, GBP) and directions (LEND, BORROW).
4. Measure:
   - **Total wall-clock time** for Phase 1.
   - **p50, p95, p99 latency** per HTTP request.
   - **Requests per second** (throughput).
   - **Error rate** (must be 0% for a valid benchmark).

### Phase 2: Read Throughput (Queries)
**Goal**: Measure query performance and projection freshness.

1. Query the **People list** (`GET /api/v1/people?page=0&size=50`). Record latency.
2. Query the **Debt list with filters** (`GET /api/v1/debts?personId={id}&status=OPEN`). Execute for 50 random person IDs.
3. Query the **Debt summary projection** (`GET /api/v1/debts/summary`).
4. Measure:
   - **p50, p95, p99 latency** per query.
   - **Result correctness**: Verify the summary totals match the individually queried debts.

### Phase 3: Eventual Consistency Latency
**Goal**: Measure cross-module event propagation time.

1. Pick **1 existing Person** and update their email (`PUT /api/v1/people/{id}`).
2. Poll the **Debt module's PersonReadModel** (via a query that joins person data) until the updated email is visible.
3. Measure:
   - **Event propagation latency**: Time from HTTP 200 on the update to the read model reflecting the change.
   - **Polling attempts** required.
4. Repeat for 10 random persons. Report average and max.

### Phase 4: Complex Transactional Workload
**Goal**: Measure optimistic locking and aggregate mutation performance.

1. Pick **100 existing Debts**.
2. For each debt, execute a **SettleDebt** command (`POST /api/v1/debts/{id}/settle`).
3. Immediately follow with a **second SettleDebt** on the same ID (must fail with `DebtAlreadySettled` or equivalent).
4. Measure:
   - **Successful settlement latency** (p50, p95, p99).
   - **Conflict rejection latency** (how fast is the domain error returned?).
   - **Aggregate version increment correctness**: Verify version bumped by exactly 1.

### Phase 5: Bulk Event Ingestion (Cold Start Simulation)
**Goal**: Measure subscriber throughput and projection hydration speed.

1. Truncate the **Debt module's projections** (not aggregates).
2. Emit a **Backfill Request** event (or trigger the hydration protocol).
3. Measure:
   - **Time to full hydration**: From backfill trigger to all 2,000 debts visible in queries.
   - **Subscriber throughput**: Events processed per second.
   - **Inbox deduplication rate**: Verify no duplicate projection writes occurred.

---

## 3. Metrics Schema (Report Format)

Every implementation MUST emit a `benchmark-report.json` file in the implementation root after `make benchmark` completes.

```json
{
  "meta": {
    "target": "rust-axum",
    "timestamp": "2026-05-21T15:24:56Z",
    "hardware": {
      "cpu": "auto-detected",
      "memory_mb": "auto-detected",
      "cores": "auto-detected"
    },
    "version": "1.0.0"
  },
  "phases": {
    "phase_0_reset": { "duration_ms": 1234 },
    "phase_1_write_throughput": {
      "duration_ms": 5678,
      "total_requests": 3001,
      "successful_requests": 3001,
      "failed_requests": 0,
      "requests_per_second": 528.5,
      "latency_ms": { "p50": 12, "p95": 45, "p99": 89 }
    },
    "phase_2_read_throughput": {
      "duration_ms": 890,
      "total_queries": 52,
      "latency_ms": { "p50": 8, "p95": 22, "p99": 41 },
      "correctness_check": "PASSED"
    },
    "phase_3_eventual_consistency": {
      "duration_ms": 2345,
      "samples": 10,
      "propagation_latency_ms": { "avg": 145, "min": 89, "max": 312 },
      "polling_attempts_avg": 2.3
    },
    "phase_4_complex_transactions": {
      "duration_ms": 1234,
      "successful_settlements": 100,
      "conflict_rejections": 100,
      "settlement_latency_ms": { "p50": 15, "p95": 38, "p99": 72 },
      "rejection_latency_ms": { "p50": 5, "p95": 12, "p99": 23 }
    },
    "phase_5_bulk_ingestion": {
      "duration_ms": 4567,
      "events_processed": 2000,
      "events_per_second": 438.2,
      "hydration_time_ms": 3456,
      "inbox_deduplication_rate": 0.0
    }
  },
  "summary": {
    "total_duration_ms": 14914,
    "overall_score": 14914
  }
}
```

**Overall Score Rule**: Lower is better. The `overall_score` is the `total_duration_ms`. For ranking, implementations are sorted by this value ascending.

---

## 4. Per-Platform Benchmark Harness

The Builder MUST generate a benchmark harness appropriate to the target stack. The harness is **not** a test; it is a standalone executable or script.

| Concern | Java / Spring | Kotlin / Ktor | Go / Gin | Rust / Axum |
|---|---|---|---|---|
| **Harness Type** | JMH micro-benchmark OR custom `BenchmarkRunner` class | Same as Java | `cmd/benchmark/main.go` | `cargo run --bin benchmark` |
| **HTTP Client** | `WebTestClient` or `RestTemplate` | Ktor `HttpClient` | `net/http` with `http.Client` | `reqwest` |
| **Timer** | `System.nanoTime()` | `System.nanoTime()` | `time.Now()` + `time.Since()` | `std::time::Instant` |
| **Report Writer** | Jackson → `benchmark-report.json` | kotlinx.serialization → JSON | `encoding/json` | `serde_json` |
| **Concurrency** | Sequential (Phase 1 may use parallel streams if idempotent) | Same as Java | Goroutines for write burst | `tokio::spawn` for write burst |

### Harness Rules
1. **No Caching**: Disable all caches (Redis, HTTP client caching, JVM JIT warmup is acceptable but must be noted in `meta`).
2. **Isolated Run**: The benchmark harness MUST connect to a fresh `make start-core` environment. It must not share infrastructure with a running dev server.
3. **Sequential by Default**: Phases run sequentially. Within a phase, requests may be concurrent only if the underlying operations are idempotent and order-independent (e.g., bulk person creation).
4. **Deterministic Data**: Use a seeded PRNG for all random selections (person IDs, currencies, etc.). Seed = `42`.
5. **Warmup Exclusion**: Phase 0 is the only warmup. Metrics start at Phase 1.

---

## 5. Makefile Integration

Add to the implementation's `Makefile`:

```makefile
benchmark: build
	@echo "Starting benchmark infrastructure..."
	$(MAKE) start-core
	@sleep 5
	@echo "Running benchmark harness..."
	# Platform-specific benchmark execution
	$(MAKE) benchmark-exec
	@echo "Benchmark complete. Report: benchmark-report.json"
	$(MAKE) stop

benchmark-compare:
	@echo "Comparing benchmark reports..."
	# This target is a placeholder for the comparison agent.
	# Usage: make benchmark-compare BASE=java-spring TARGET=rust-axum
```

**Mandatory Targets:**
- `make benchmark`: Executes the full benchmark protocol and emits `benchmark-report.json`.
- `make benchmark-compare BASE=[TargetA] TARGET=[TargetB]`: Invokes the comparison agent (or script) to diff two reports.

---

## 6. Cross-Implementation Comparison Protocol

When `BENCHMARK [Target A] vs [Target B]` is invoked, the system:

1. Ensures both targets have a `benchmark-report.json` (run `make benchmark` if missing).
2. Loads both JSON reports.
3. Validates schema version compatibility.
4. Produces a comparison report:

```markdown
# Benchmark Comparison: rust-axum vs java-spring

## Overall Winner: rust-axum (14,914 ms vs 23,441 ms — 36% faster)

### Phase Breakdown
| Phase | rust-axum | java-spring | Delta | Winner |
|---|---|---|---|---|
| Write Throughput | 5,678 ms | 9,120 ms | -37.7% | rust-axum |
| Read Throughput | 890 ms | 1,230 ms | -27.6% | rust-axum |
| Event Consistency | 2,345 ms | 3,890 ms | -39.7% | rust-axum |
| Complex TX | 1,234 ms | 2,100 ms | -41.2% | rust-axum |
| Bulk Ingestion | 4,567 ms | 7,101 ms | -35.7% | rust-axum |

### Key Observations
- **rust-axum** shows consistently lower latency across all phases, likely due to zero-cost abstractions and tokio's work-stealing scheduler.
- **java-spring** has higher p99 latency in Phase 1, possibly due to GC pauses during bulk insertions.
- Event propagation (Phase 3) is the narrowest margin; both stacks use the same RabbitMQ broker, so the difference is purely consumer processing speed.
```

The comparison agent MUST NOT fabricate hardware specs. If hardware differs between runs, it MUST flag this as a confounding variable.
