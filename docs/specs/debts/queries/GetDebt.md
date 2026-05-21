# Use Case: Get Debt

## Goal
Retrieve details of a specific debt.

## Flow
1. Receive `debtId`.
2. Query the Store for [[models/Debt]] with the given `debtId`.
3. If not found, return `DebtNotFound`.
4. Return the [[models/Debt]].

## Errors
- `DebtNotFound`

## Result
- [[models/Debt]]
