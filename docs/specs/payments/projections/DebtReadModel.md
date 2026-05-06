# Model: Debt Read Model

This model is a local, replicated representation of a Debt, maintained entirely by the Payments module to decouple payment validation from the Debts module.

## Properties
- `id`: UUID (Primary Key, matches the original Debt ID)
- `tenantId`: UUID (The owner of the original Debt)
- `currentBalance`: Integer (Used to validate that a payment does not exceed the remaining balance)
- `isSettled`: Boolean (To prevent registering payments against settled debts)

## Constraints
- This model is strictly read-only for the core domain. It is only mutated by the `SyncDebt` event handler based on incoming `DebtRegistered` and `DebtSettled` events.
