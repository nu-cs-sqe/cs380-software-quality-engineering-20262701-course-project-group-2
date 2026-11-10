# BVA Analysis: `King`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `King` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `King` object with the same color as the original piece.

| Test Case ID                                                      | Method(s) under test | State of the System | Expected Output                                             | Implemented? |
|-------------------------------------------------------------------|----------------------|---------------------|-------------------------------------------------------------|--------------|
| KC1<br><sub>makeCopy_WhiteKing_ReturnEquivalentDistinctKing</sub> | `makeCopy()`         | WHITE king          | a distinct `King` object with type `KING` and color `WHITE` | :white_check_mark:          |
| KC2<br><sub>makeCopy_BlackKing_ReturnEquivalentDistinctKing</sub> | `makeCopy()`         | BLACK king          | a distinct `King` object with type `KING` and color `BLACK` | :x:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the king's normal one-square movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns the eight squares one step away from `from` in any direction, clipped to the board (five on an edge, three in a corner). 

| Test Case ID                                                                  | Method(s) under test | State of the System                                                                                                                                                                                           | Expected Output                                                    | Implemented? |
|-------------------------------------------------------------------------------|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------|--------------|
| M22<br><sub>getValidMoves_King_AllDirectionsFromCentre_returnEight</sub>      | `getValidMoves()`    | single white king on (3, 3), every other square empty<br>• from: (3, 3) (white king, not on an edge, nothing to block it)                                                                                     | `{(2, 2), (2, 3), (2, 4), (3, 2), (3, 4), (4, 2), (4, 3), (4, 4)}` | :x:          |
| M46<br><sub>getValidMoves_King_CornerOpen_returnThree</sub>                   | `getValidMoves()`    | single white king on (0, 0), every other square empty<br>• from: (0, 0)                                                                                                                                       | `{(0, 1), (1, 0), (1, 1)}`   | :X:          |
| M64<br><sub>getValidMoves_King_CornerTopLeft_returnThree</sub> | `getValidMoves()` | single white king on (0, 0) (the top-left corner), every other square empty<br>• from: (0, 0) | `{(0, 1), (1, 0), (1, 1)}` — five of the eight steps land off the board | :x: |
| M65<br><sub>getValidMoves_King_CornerTopRight_returnThree</sub> | `getValidMoves()` | single white king on (7, 0) (the top-right corner), every other square empty<br>• from: (7, 0) | `{(6, 0), (6, 1), (7, 1)}` — five of the eight steps land off the board | :x: |
| M66<br><sub>getValidMoves_King_CornerBottomLeft_returnThree</sub> | `getValidMoves()` | single white king on (0, 7) (the bottom-left corner), every other square empty<br>• from: (0, 7) | `{(0, 6), (1, 6), (1, 7)}` — five of the eight steps land off the board | :x: |
| M67<br><sub>getValidMoves_King_CornerBottomRight_returnThree</sub> | `getValidMoves()` | single white king on (7, 7) (the bottom-right corner), every other square empty<br>• from: (7, 7) | `{(6, 6), (6, 7), (7, 6)}` — five of the eight steps land off the board | :x: |

Cases M64–M67 are the boundary values for `getValidMoves()`: one per corner, where five of the eight one-square steps leave the board and exactly three destinations remain. M46 already stands on (0, 0); M64 is kept so the corner series is complete.

Cases M14, M15 and M21 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.
