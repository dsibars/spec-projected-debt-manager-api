# Definitions: Debt Domain

This document defines the Ubiquitous Language for the Debt management domain.

## Core Terms

- **Debt**: A financial obligation between the user and another person.
- **Direction**: The flow of the debt (`OWED_TO_ME` or `I_OWE`).
- **Total Amount**: The initial principal amount of the debt.
- **Current Balance**: The remaining amount yet to be settled (updated via the `payments` module).
- **Settled State**: The status of a debt when its **Current Balance** reaches zero.
- **Overdue**: A state where a debt's **Due Date** has passed but it is not yet **Settled**.
