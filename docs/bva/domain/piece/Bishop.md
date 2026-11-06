# BVA Analysis: `Bishop`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `Bishop` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `Bishop` object with the same color as the original piece.

| Test Case ID                                                          | Method(s) under test | State of the System | Expected Output                                                 | Implemented? |
|-----------------------------------------------------------------------|----------------------|---------------------|-----------------------------------------------------------------|--------------|
| BC1<br><sub>makeCopy_WhiteBishop_ReturnEquivalentDistinctBishop</sub> | `makeCopy()`         | WHITE bishop        | a distinct `Bishop` object with type `BISHOP` and color `WHITE` | :X:          |
| BC2<br><sub>makeCopy_BlackBishop_ReturnEquivalentDistinctBishop</sub> | `makeCopy()`         | BLACK bishop        | a distinct `Bishop` object with type `BISHOP` and color `BLACK` | :X:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the `Bishop`'s normal movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns every square on the four diagonals running out from `from`, stopping only at the board edge. No other square on the board is read, so the sweep continues through occupied squares instead of stopping at a blocker and never treats a square as a capture; blockers, captures, king safety and castling are decided by [`Game.getLegalMoves()`](../Game.md).

| Test Case ID                                                                  | Method(s) under test | State of the System                                                                                                                                                                                                 | Expected Output                                                                                                                                          | Implemented? |
|-------------------------------------------------------------------------------|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M45<br><sub>getValidMoves_Bishop_AllDirectionsFromCentre_returnThirteen</sub> | `getValidMoves()`    | single white bishop on (3, 3), every other square empty<br>• from: (3, 3)                                                                                                                                           | `{(0, 0), (1, 1), (2, 2), (4, 4), (5, 5), (6, 6), (7, 7), (0, 6), (1, 5), (2, 4), (4, 2), (5, 1), (6, 0)}`                                               | :X:          |

Cases M10, M11 and M12 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.
