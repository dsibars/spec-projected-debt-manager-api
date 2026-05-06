# Language Skill: Go

## Standards
- **Version**: Go 1.22+.
- **Style Guide**: Follow `gofmt` and [Go Code Review Comments](https://github.com/golang/go/wiki/CodeReviewComments).

## Best Practices
- **Simplicity**: Favor explicit code over "magical" abstractions.
- **Composition**: Use interfaces and embedding over inheritance.
- **Concurrency**: Use goroutines and channels for concurrent tasks. "Don't communicate by sharing memory; share memory by communicating."
- **Context**: Pass `context.Context` for cancellation and timeouts across API boundaries.

## Error Handling
- Use explicit error checking (`if err != nil`).
- Wrap errors with context using `fmt.Errorf` or `errors.Join`.
- Use `errors.Is` and `errors.As` for error inspection.
