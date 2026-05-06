# Definitions: Identity Domain

This document defines the core concepts of Identity and Access Management (IAM).

## Core Terms
- **User**: The technical identity authorized to access the system.
- **Credential**: A secret (Password, Token, OAuth ID) used to prove identity.
- **Provider**: The authority that validates a credential (e.g., LOCAL, GOOGLE, APPLE).
- **Session**: A temporary authorized state for a User.
- **Access Token**: A cryptographically signed proof of an active session (e.g., JWT).
- **Refresh Token**: A long-lived credential used to obtain new Access Tokens.
- **MFA**: Multi-Factor Authentication (e.g., TOTP, SMS).
