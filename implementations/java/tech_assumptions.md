# Technical Assumptions Ledger

This file contains the "vibe-based" technical choices made by the Builder that are not explicitly defined in the Shared Skills.

- [2026-05-10] - Spring Boot 3.2.4 - Used as the concrete version for the 3.2.x requirement in `spring.md`. - System Core
- [2026-05-10] - H2 Database for tests - Used to allow running `mvn test` without a full PostgreSQL infrastructure during the skeleton phase. - Testing
- [2026-05-10] - Maven Wrapper 3.9.12 - Used to match the environment's Maven version. - DevOps
