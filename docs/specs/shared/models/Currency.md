# Value Object: Currency

Represents an ISO 4217 currency code.

## Properties
- `code`: The 3-letter string currency code.

## Constraints
- Must be exactly 3 uppercase letters.
- Must be a recognized ISO 4217 code (e.g., "USD", "EUR", "GBP").
- Must be immutable.
- Equality is based on the `code`.
