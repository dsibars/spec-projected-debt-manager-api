# DevOps Skill: Maven

## Standards
- **Wrapper**: Always use the Maven Wrapper (`mvnw`) to ensure consistent build environments.
- **pom.xml**: Maintain a clean `pom.xml`. Group dependencies logically (e.g., Spring Boot starters, persistence, testing).
- **Plugins**: Ensure the Spring Boot Maven Plugin is configured for executable JAR generation.

## Lifecycle Goals
- `clean`: Removes the `target/` directory.
- `compile`: Compiles the source code.
- `test`: Executes tests.
- `package`: Packages the compiled code into a distributable format (e.g., JAR).
