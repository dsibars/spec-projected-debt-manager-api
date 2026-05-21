# Skill: Universal Type Mapping

## Category: meta
## Provides:
- Type Mapping
## Conflicts With:
- None
## Depends On:
- None


## Purpose
Defines how abstract spec types map to concrete types in each target language.

## Spec Type Definitions

| Spec Type | Meaning | Constraints |
|-----------|---------|-------------|
| `String` | Unicode text | Max length defined by consuming skill |
| `Integer` | Whole number | Context-dependent bit-width |
| `Decimal` | Arbitrary precision decimal | For financial calculations |
| `Boolean` | true/false | |
| `DateTime` | ISO 8601 timestamp | With timezone |
| `UUID` | Universally unique identifier | Version 4 |
| `Enum<T>` | Closed set of values | Values listed in model spec |

## Language Mappings

| Spec Type | Java/Kotlin | Go | Rust | TypeScript |
|-----------|-------------|-----|------|------------|
| `String` | `String` | `string` | `String` | `string` |
| `Integer` | `Long` | `int64` | `i64` | `number` |
| `Integer (count/index)` | `Integer` | `int` | `i32` | `number` |
| `Decimal` | `BigDecimal` | `decimal.Decimal` | `rust_decimal::Decimal` | `number` (cents pattern preferred) |
| `Boolean` | `boolean` | `bool` | `bool` | `boolean` |
| `DateTime` | `OffsetDateTime` | `time.Time` | `chrono::DateTime<Utc>` | `Date` |
| `UUID` | `java.util.UUID` | `uuid.UUID` | `uuid::Uuid` | `string` |
| `Enum<T>` | `enum class` | `const` + `iota` | `enum` | union types |

## Nullable Mapping Rule
Any spec property marked with `?` (optional) MUST be projected as a nullable type in the target language:
- Kotlin: `T?`
- Java: `Optional<T>`
- Go: `*T`
- Rust: `Option<T>`
- TypeScript: `T | undefined`

## Financial Integer Rule
Any property representing currency (amount, balance, payment) MUST use the 64-bit mapping regardless of magnitude, to prevent overflow.

## Type Override Rule
If a model spec explicitly declares a type (e.g., `String/UUID`), the explicit declaration takes precedence over the default mapping.