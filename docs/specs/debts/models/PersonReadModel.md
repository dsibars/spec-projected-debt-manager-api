# Model: Person Read Model

This model is a local, replicated representation of a Person, maintained entirely by the Debts module to decouple validation from the People module.

## Properties
- `id`: UUID (Primary Key, matches the original Person ID)
- `name`: String (Replicated for easy display on Debts)
- `isArchived`: Boolean (To prevent creating debts for deleted users)

## Constraints
- This model is strictly read-only for the core domain. It is only mutated by the `SyncPerson` event handler.
