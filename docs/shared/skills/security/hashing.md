# Skill: Secure Password Hashing

## Category: security
## Provides:
- Hashing
## Conflicts With:
- jwt
- oauth-integration
- pii-management
- traffic-governance
## Depends On:
- None explicitly declared


This skill defines the technical law for protecting user credentials at rest.

## Principles
1.  **Slow and Salted**: Use a slow, memory-hard hashing algorithm to resist brute-force and rainbow table attacks.
2.  **Unique Salts**: Every password MUST have a unique, randomly generated salt.
3.  **No Plaintext**: Plaintext passwords MUST NEVER be logged, cached, or stored.

## Technical Requirements
- **Algorithm**: `Argon2id` is the preferred algorithm. If not available in the target stack, `BCrypt` (with a cost factor of at least 12) MUST be used.
- **Parameters (Argon2id)**:
  - Iterations: 3
  - Memory: 64MB
  - Parallelism: 1
- **Storage**: The stored secret MUST include the algorithm identifier, salt, and the hash itself (e.g., Modular Crypt Format).

## Implementation Interface
The Builder must synthesize a `HashingPort` with two methods:
- `hash(plaintext: String): String`
- `verify(plaintext: String, hashed: String): Boolean`