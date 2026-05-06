# Use Case: Get Person

## Goal
Retrieve the details of a specific person using their unique identifier.

## Flow
1. Receive `id`.
2. Attempt to find the [[models/Person]] with the matching `id` in the Store.
3. If not found, return an error.
4. Return the found [[models/Person]].

## Errors
- `PersonNotFound`: If no person exists with the provided `id`.
