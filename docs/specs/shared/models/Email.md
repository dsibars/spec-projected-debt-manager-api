# Value Object: Email

Represents a valid electronic mail address.

## Properties
- `value`: The string representation of the email address.

## Constraints
- Must not be empty.
- Must conform to a standard email regex format (e.g., `^[A-Za-z0-9+_.-]+@(.+)$`).
- Must be immutable.
- Equality is based on the normalized (lowercase) `value`.
