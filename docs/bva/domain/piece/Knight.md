# BVA Analysis: `Knight`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `Knight` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `Knight` object with the same color as the original piece.

| Test Case ID                                                          | Method(s) under test | State of the System | Expected Output                                                 | Implemented? |
|-----------------------------------------------------------------------|----------------------|---------------------|-----------------------------------------------------------------|--------------|
| NC1<br><sub>makeCopy_WhiteKnight_ReturnEquivalentDistinctKnight</sub> | `makeCopy()`         | WHITE knight        | a distinct `Knight` object with type `KNIGHT` and color `WHITE` | :X:          |
| NC2<br><sub>makeCopy_BlackKnight_ReturnEquivalentDistinctKnight</sub> | `makeCopy()`         | BLACK knight        | a distinct `Knight` object with type `KNIGHT` and color `BLACK` | :X:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the `Knight`'s normal movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns the squares a knight reaches from `from`: two along one axis and one along the other, in all eight combinations, clipped to the board (eight squares in the interior, fewer on an edge or corner). No other square on the board is read, so a destination is returned whatever occupies it and nothing can block the jump; occupied squares, captures, king safety and castling are decided by [`Game.getLegalMoves()`](../Game.md).

| Test Case ID                                                                         | Method(s) under test | State of the System                                                                                                                                                                           | Expected Output                                                                                                                                                                | Implemented? |
|--------------------------------------------------------------------------------------|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M25<br><sub>getValidMoves_Knight_AllDirectionsFromCentre_returnEight</sub>           | `getValidMoves()`    | a single white knight on (3, 3), every other square empty<br>• from: (3, 3)                                                                                                                   | `{(1, 2), (1, 4), (2, 1), (2, 5), (4, 1), (4, 5), (5, 2), (5, 4)}`                                                                                                             | :X:          |

Cases M13, M19 and M20 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.
