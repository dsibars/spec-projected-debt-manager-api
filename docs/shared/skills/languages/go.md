# Language Skill: Go

## Standards
- **Version**: Go 1.22+.
- **Style Guide**: Follow `gofmt` and [Go Code Review Comments](https://github.com/golang/go/wiki/CodeReviewComments).

## Best Practices
- **Simplicity**: Favor explicit code over "magical" abstractions.
- **Composition**: Use interfaces and embedding over inheritance.
- **Concurrency**: Use goroutines and channels for concurrent tasks. "Don't communicate by sharing memory; share memory by communicating."
- **Context**: Pass `context.Context` for cancellation and timeouts across API boundaries.
- **Type Mapping Law**: Any property marked as optional (e.g., `email?`) or nullable in the specifications MUST be projected as a pointer type `*T` in the Go implementation to support explicit nil-checking.

## Domain Errors & Handling
- **No Exceptions**: Go does not have exceptions. The Builder must NEVER translate DDD "Exceptions" into `panic()` calls.
- **Custom Error Types**: Explicit Domain Errors defined in the specs must be synthesized as custom `struct` types implementing the standard `error` interface (e.g., `type InvalidEmailError struct{}`).
- **Return Signatures**: Use Cases must return a tuple of `(Type, error)`.
- **Inspection**: The Presentation layer must use `errors.As()` to inspect the exact Domain Error struct and map it to the appropriate HTTP status code.

## Idiomatic Project Structure
- **Source Root**: `internal/` (For domain business logic and modules).
- **Entrypoint Root**: `cmd/server/` (Main application executable).
- **Configuration**: Modules must be projected strictly as subpackages of `internal/`.
