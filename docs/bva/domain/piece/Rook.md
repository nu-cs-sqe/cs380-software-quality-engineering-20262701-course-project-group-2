# BVA Analysis: `Rook`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `Rook` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `Rook` object with the same color as the original piece.

| Test Case ID                                                      | Method(s) under test | State of the System | Expected Output                                             | Implemented? |
|-------------------------------------------------------------------|----------------------|---------------------|-------------------------------------------------------------|--------------|
| RC1<br><sub>makeCopy_WhiteRook_ReturnEquivalentDistinctRook</sub> | `makeCopy()`         | WHITE rook          | a distinct `Rook` object with type `ROOK` and color `WHITE` | :X:          |
| RC2<br><sub>makeCopy_BlackRook_ReturnEquivalentDistinctRook</sub> | `makeCopy()`         | BLACK rook          | a distinct `Rook` object with type `ROOK` and color `BLACK` | :X:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the `Rook`'s normal movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns every square on the row and the column through `from`, stopping only at the board edge. No other square on the board is read, so the sweep continues through occupied squares instead of stopping at a blocker and never treats a square as a capture; blockers, captures, king safety and castling are decided by [`Game.getLegalMoves()`](../Game.md).

| Test Case ID                                                                | Method(s) under test | State of the System                                                                                                                                    | Expected Output                                                                                                       | Implemented? |
|-----------------------------------------------------------------------------|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|--------------|
| M8<br><sub>getValidMoves_Rook_AllTilesUnblocked_returnAllRowAndColumn</sub> | `getValidMoves()`    | single white rook on (7, 7), every other square empty<br>• from: (7, 7)                                                                                | `{(0, 7), (1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7), (7, 0), (7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6)}`    | :X:          |

Cases M6, M7 and M9 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.
