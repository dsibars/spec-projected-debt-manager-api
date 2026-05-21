# Language Skill: Go

## Category: languages
## Provides:
- Go
## Conflicts With:
- java
- kotlin
- rust
## Depends On:
- None explicitly declared


## Standards
- **Version**: Go 1.22+.
- **Style Guide**: Follow `gofmt` and [Go Code Review Comments](https://github.com/golang/go/wiki/CodeReviewComments).
- **Linting**: `golangci-lint` is MANDATORY in CI with the following linters enabled: `errcheck`, `gosimple`, `govet`, `ineffassign`, `staticcheck`, `unused`.

## Best Practices
- **Simplicity**: Favor explicit code over "magical" abstractions.
- **Composition**: Use interfaces and embedding over inheritance.
- **Concurrency**: Use goroutines and channels for concurrent tasks. "Don't communicate by sharing memory; share memory by communicating."
- **Context**: Pass `context.Context` for cancellation and timeouts across ALL API boundaries (DB, HTTP, AMQP).
- **Type Mapping Law**: Any property marked as optional (e.g., `email?`) or nullable in the specifications MUST be projected as a pointer type `*T` in the Go implementation to support explicit nil-checking.

## Type Mapping Law

| Spec Type | Go Type | Notes |
|---|---|---|
| `String` | `string` | |
| `Integer` | `int` | |
| `Long` | `int64` | |
| `Boolean` | `bool` | |
| `Decimal` | `shopspring/decimal.Decimal` | Use `shopspring/decimal` package |
| `DateTime` | `time.Time` | UTC by convention |
| `UUID` | `google/uuid.UUID` | |
| `optional<T>` | `*T` | Pointer for nilability |
| `List<T>` | `[]T` | Slice |
| `Map<K,V>` | `map[K]V` | |

## Domain Errors & Handling
- **No Exceptions**: Go does not have exceptions. The Builder must NEVER translate DDD "Exceptions" into `panic()` calls.
- **Custom Error Types**: Explicit Domain Errors defined in the specs must be synthesized as custom `struct` types implementing the standard `error` interface (e.g., `type InvalidEmailError struct{}`).
- **Error Wrapping**: Use `fmt.Errorf("...: %w", err)` to wrap errors for stack trace context.
- **Return Signatures**: Use Cases must return a tuple of `(Type, error)`.
- **Inspection**: The Presentation layer must use `errors.As()` to inspect the exact Domain Error struct and map it to the appropriate HTTP status code.

## Idiomatic Project Structure
- **Source Root**: `internal/` (For domain business logic and modules).
- **Entrypoint Root**: `cmd/api/` and `cmd/worker/` (Main application executables).
- **Configuration**: Modules must be projected strictly as subpackages of `internal/`.
- **Public API**: Only `cmd/` and `pkg/` (if any shared libraries) are at the top level.

## Recommended Package Ecosystem
| Concern | Package |
|---|---|
| Web Framework | `gin-gonic/gin` |
| DB Driver + Pool | `jackc/pgx/v5` (use `pgxpool`) |
| Migrations | `golang-migrate/migrate/v4` |
| Validation | `go-playground/validator/v10` |
| AMQP Client | `rabbitmq/amqp091-go` |
| Redis | `redis/go-redis/v9` |
| JWT | `golang-jwt/jwt/v5` |
| Logging | `rs/zerolog` |
| Testing | `stretchr/testify` |
| Configuration | `spf13/viper` or `kelseyhightower/envconfig` |
