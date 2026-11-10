# BVA Analysis: `Bishop`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `Bishop` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `Bishop` object with the same color as the original piece.

| Test Case ID                                                          | Method(s) under test | State of the System | Expected Output                                                 | Implemented? |
|-----------------------------------------------------------------------|----------------------|---------------------|-----------------------------------------------------------------|--------------|
| BC1<br><sub>makeCopy_WhiteBishop_ReturnEquivalentDistinctBishop</sub> | `makeCopy()`         | WHITE bishop        | a distinct `Bishop` object with type `BISHOP` and color `WHITE` | :white_check_mark:          |
| BC2<br><sub>makeCopy_BlackBishop_ReturnEquivalentDistinctBishop</sub> | `makeCopy()`         | BLACK bishop        | a distinct `Bishop` object with type `BISHOP` and color `BLACK` | :white_check_mark:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the `Bishop`'s normal movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns every square on the four diagonals running out from `from`, stopping only at the board edge. 

| Test Case ID                                                                  | Method(s) under test | State of the System                                                                                                                                                                                                 | Expected Output                                                                                                                                          | Implemented? |
|-------------------------------------------------------------------------------|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M45<br><sub>getValidMoves_Bishop_AllDirectionsFromCentre_returnThirteen</sub> | `getValidMoves()`    | single white bishop on (3, 3), every other square empty<br>• from: (3, 3)                                                                                                                                           | `{(0, 0), (1, 1), (2, 2), (4, 4), (5, 5), (6, 6), (7, 7), (0, 6), (1, 5), (2, 4), (4, 2), (5, 1), (6, 0)}`                                               | :X:          |
| M56<br><sub>getValidMoves_Bishop_CornerTopLeft_returnSeven</sub> | `getValidMoves()` | single white bishop on (0, 0) (the top-left corner), every other square empty<br>• from: (0, 0) | `{(1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7)}` — only one of the four diagonals is on the board | :white_check_mark: |
| M57<br><sub>getValidMoves_Bishop_CornerTopRight_returnSeven</sub> | `getValidMoves()` | single white bishop on (7, 0) (the top-right corner), every other square empty<br>• from: (7, 0) | `{(6, 1), (5, 2), (4, 3), (3, 4), (2, 5), (1, 6), (0, 7)}` — only one of the four diagonals is on the board | :white_check_mark: |
| M58<br><sub>getValidMoves_Bishop_CornerBottomLeft_returnSeven</sub> | `getValidMoves()` | single white bishop on (0, 7) (the bottom-left corner), every other square empty<br>• from: (0, 7) | `{(1, 6), (2, 5), (3, 4), (4, 3), (5, 2), (6, 1), (7, 0)}` — only one of the four diagonals is on the board | :white_check_mark: |
| M59<br><sub>getValidMoves_Bishop_CornerBottomRight_returnSeven</sub> | `getValidMoves()` | single white bishop on (7, 7) (the bottom-right corner), every other square empty<br>• from: (7, 7) | `{(6, 6), (5, 5), (4, 4), (3, 3), (2, 2), (1, 1), (0, 0)}` — only one of the four diagonals is on the board | :white_check_mark: |

Cases M56–M59 are the boundary values for `getValidMoves()`: one per corner, where three of the four diagonals leave the board immediately, so the bishop returns the 7 squares of the one remaining diagonal instead of the 13 it has in the middle.

Cases M10, M11 and M12 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.
