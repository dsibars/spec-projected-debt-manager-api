# Skill: Data Seeding and Bootstrapping

This skill defines how the system initializes its state for development, testing, and production environments.

## 1. Principles
- **Deterministic Initial State**: Every environment must be capable of reaching a "Ready" state automatically.
- **Separation of Concerns**: Migrations handle structural changes (DDL); Seeding handles initial data (DML).
- **Environment Awareness**: Seeding logic must be configurable per environment (e.g., more verbose data in `dev`, minimal in `prod`).

## 2. Bootstrapping the System (The First Tenant)
For the system to allow registration, at least one ACTIVE tenant must exist (as per the Tenant Load Balancing strategy).

- **The Seed Migration**: Each implementation MUST provide a `V0__seed_initial_tenant.sql` migration or a code-based seeder that inserts a "Default Tenant" into the `identity.tenants` table if it is empty.
- **Local Dev**: `make infra-up` must result in a database with at least one tenant ready.

## 3. Environment-Specific Seeding
- **Development**: Seeding should include a range of dummy users, people, and debts to allow immediate manual testing.
- **Testing**:
    - Integration and Application tests MUST NOT rely on persistent database state.
    - The test harness MUST seed a "Test Tenant" and a "Test User" before each test execution if the test requires an authenticated context.
- **Production**: Seeding should be strictly limited to the absolute minimum required for the system to function (e.g., initial system roles or the first bootstrap tenant).

## 4. Implementation Requirements
- **Idempotency**: Seeding scripts MUST use `INSERT ... ON CONFLICT DO NOTHING` or similar logic to prevent duplicate data errors on restarts.
- **Makefile Interface**:
    - `make seed`: Executes the seeder for the current environment.
    - `make infra-up` should automatically trigger seeding in the `dev` profile.
