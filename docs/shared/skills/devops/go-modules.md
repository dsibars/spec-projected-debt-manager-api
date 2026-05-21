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

## Makefile Integration
- `make build`: Must execute `go build ./...`.
- `make test`: Must execute `go test ./...`.
- `make run`: Must execute `go run cmd/main.go` (or similar entry point).

## Best Practices
- Use `go mod tidy` to prune dependencies.
- Vendor dependencies (`go mod vendor`) only if explicitly required by the implementation config.