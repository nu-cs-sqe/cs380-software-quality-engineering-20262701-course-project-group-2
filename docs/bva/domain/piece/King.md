# BVA Analysis: `King`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `King` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `King` object with the same color as the original piece.

| Test Case ID                                                      | Method(s) under test | State of the System | Expected Output                                             | Implemented? |
|-------------------------------------------------------------------|----------------------|---------------------|-------------------------------------------------------------|--------------|
| KC1<br><sub>makeCopy_WhiteKing_ReturnEquivalentDistinctKing</sub> | `makeCopy()`         | WHITE king          | a distinct `King` object with type `KING` and color `WHITE` | :X:          |
| KC2<br><sub>makeCopy_BlackKing_ReturnEquivalentDistinctKing</sub> | `makeCopy()`         | BLACK king          | a distinct `King` object with type `KING` and color `BLACK` | :X:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the king's normal one-square movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns the eight squares one step away from `from` in any direction, clipped to the board (five on an edge, three in a corner). No other square on the board is read, so a destination is returned whatever occupies it and an attacked square is still returned here; occupied squares, check safety and castling are decided by [`Game.getLegalMoves()`](../Game.md), and castling is never returned by this method.

| Test Case ID                                                                  | Method(s) under test | State of the System                                                                                                                                                                                           | Expected Output                                                    | Implemented? |
|-------------------------------------------------------------------------------|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------|--------------|
| M22<br><sub>getValidMoves_King_AllDirectionsFromCentre_returnEight</sub>      | `getValidMoves()`    | single white king on (3, 3), every other square empty<br>• from: (3, 3) (white king, not on an edge, nothing to block it)                                                                                     | `{(2, 2), (2, 3), (2, 4), (3, 2), (3, 4), (4, 2), (4, 3), (4, 4)}` | :X:          |
| M46<br><sub>getValidMoves_King_CornerOpen_returnThree</sub>                   | `getValidMoves()`    | single white king on (0, 0), every other square empty<br>• from: (0, 0)                                                                                                                                       | `{(0, 1), (1, 0), (1, 1)}` — no off-board locations are returned   | :X:          |

Cases M14, M15 and M21 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.
