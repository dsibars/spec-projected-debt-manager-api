# DevOps Skill: Maven

## Category: devops
## Provides:
- Maven
## Conflicts With:
- cargo
- configuration-management
- dockerization
- go-modules
- health-probes
## Depends On:
- None explicitly declared


## Standards
- **Wrapper**: Always use the Maven Wrapper (`mvnw`) to ensure consistent build environments.
- **pom.xml**: Maintain a clean `pom.xml`. Group dependencies logically (e.g., Spring Boot starters, persistence, testing).
- **Plugins**: Ensure the Spring Boot Maven Plugin is configured for executable JAR generation.

## Multi-Binary Build (Api vs Worker)
To support the "Binary Structural Law", the Builder MUST configure the `pom.xml` with specialized profiles or execution blocks:

### Profile-Based Approach (Recommended)
```xml
<profiles>
  <profile>
    <id>api</id>
    <properties>
      <start-class>com.company.app.ApiRunner</start-class>
    </properties>
  </profile>
  <profile>
    <id>worker</id>
    <properties>
      <start-class>com.company.app.WorkerRunner</start-class>
    </properties>
  </profile>
</profiles>
```

Build commands:
- `mvnw clean package -Papi` → produces `api-*.jar`
- `mvnw clean package -Pworker` → produces `worker-*.jar`

### Multi-Module Approach (Alternative)
For large projects, use a Maven multi-module structure:
```
pom.xml (parent)
  domain/
  application/
  infrastructure/
  api/ (depends on infrastructure)
  worker/ (depends on infrastructure)
```

Both modules share `domain/`, `application/`, and `infrastructure/`.

## Lifecycle Goals
- `clean`: Removes the `target/` directory.
- `compile`: Compiles the source code.
- `test`: Executes tests.
- `package`: Packages the compiled code into distributable JARs.

## Makefile Integration
- `make build`: Must execute `./mvnw clean package` (builds API profile by default) or both profiles sequentially.
- `make test`: Must execute `./mvnw test`.
- `make run`: Must execute `./mvnw spring-boot:run -Papi`.
- `make run-worker`: Must execute `./mvnw spring-boot:run -Pworker`.
- `make migrate`: Flyway migrations run automatically on boot. For standalone execution: `./mvnw flyway:migrate`.
