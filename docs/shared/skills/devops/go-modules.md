# Skill: DevOps - Go Modules

## Category: devops
## Provides:
- Go Modules
## Conflicts With:
- cargo
- configuration-management
- dockerization
- health-probes
- maven
## Depends On:
- None explicitly declared


This skill defines the standards for dependency management and build orchestration in Go-based implementations.

## Standards
- **Dependency Management**: Use `go modules` (`go.mod`, `go.sum`).
- **Tooling**: Use the standard `go` toolchain.
- **Project Structure**: Follow the [Standard Go Project Layout](https://github.com/golang-standards/project-layout) if applicable, or project modules into a flat `internal/` structure to ensure encapsulation.

## Multi-Binary Layout
The Builder MUST produce two executables from the same module:

```
go.mod
cmd/
  api/
    main.go         # Gin HTTP server entrypoint
  worker/
    main.go         # AMQP consumer entrypoint
internal/
  modules/          # Domain + Application + Infrastructure
  shared/           # Cross-cutting concerns
```

Both `cmd/api/main.go` and `cmd/worker/main.go` import packages from `internal/` only. No code duplication between binaries.

## Makefile Integration
- `make build`: Must execute `go build -o bin/api ./cmd/api && go build -o bin/worker ./cmd/worker`.
- `make test`: Must execute `go test ./...`.
- `make run`: Must execute `go run ./cmd/api`.
- `make run-worker`: Must execute `go run ./cmd/worker`.
- `make migrate`: Must execute `migrate -path migrations -database "$SPD_DB_PRIMARY_URL" up`.

## Best Practices
- Use `go mod tidy` to prune dependencies.
- Vendor dependencies (`go mod vendor`) only if explicitly required by the implementation config.
- Pin dependency versions in `go.mod` using semantic version tags.
- Use `golangci-lint` in CI with a configuration file (`.golangci.yml`).
