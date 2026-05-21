# Skill: Global Data Standards

## Category: standards
## Provides:
- Data Formats
## Conflicts With:
- None
## Depends On:
- None explicitly declared


This skill defines the mandatory data formats and serialization standards to ensure technical interoperability across all projected modules and implementations.

## 1. Temporal Standards (Date & Time)
- **Timezone**: All timestamps MUST be stored, processed, and transmitted in **UTC**.
- **Format**: All date-time strings in the API and Event Bus MUST adhere to **ISO-8601** (e.g., `2024-05-06T20:00:00Z`).
- **Precision**: Minimum precision of milliseconds is required.

## 2. Monetary Standards (Money & Currency)
- **Precision**: To avoid floating-point rounding errors, all monetary amounts MUST be represented as **Integers** in the smallest unit of the currency (e.g., Cents for USD/EUR).
- **Type**: Use `BigInt` (or `Long` in JVM) for all amount properties.

## 3. Identity Standards
- **Format**: All `id` fields MUST be strings in the **UUID v4** (canonical 36-character) format or **ULID** format, as specified in the persistence skill.