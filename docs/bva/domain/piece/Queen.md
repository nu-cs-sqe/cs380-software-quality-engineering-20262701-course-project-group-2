# BVA Analysis: `Queen`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `Queen` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `Queen` object with the same color as the original piece.

| Test Case ID                                                        | Method(s) under test | State of the System | Expected Output                                               | Implemented? |
|---------------------------------------------------------------------|----------------------|---------------------|---------------------------------------------------------------|--------------|
| QC1<br><sub>makeCopy_WhiteQueen_ReturnEquivalentDistinctQueen</sub> | `makeCopy()`         | WHITE queen         | a distinct `Queen` object with type `QUEEN` and color `WHITE` | :white_check_mark:          |
| QC2<br><sub>makeCopy_BlackQueen_ReturnEquivalentDistinctQueen</sub> | `makeCopy()`         | BLACK queen         | a distinct `Queen` object with type `QUEEN` and color `BLACK` | :white_check_mark:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the `Queen`'s normal movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns every square on the row, the column and the two diagonals through `from`, stopping only at the board edge. No other square on the board is read, so the sweep continues through occupied squares instead of stopping at a blocker and never treats a square as a capture; blockers, captures, king safety and castling are decided by [`Game.getLegalMoves()`](../Game.md).

| Test Case ID                                                                    | Method(s) under test | State of the System                                                                                                                                                                                                                                                         | Expected Output                                                                                                                                                                                                                                                                                     | Implemented? |
|---------------------------------------------------------------------------------|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M24<br><sub>getValidMoves_Queen_AllDirectionsFromCentre_returnTwentySeven</sub> | `getValidMoves()`    | single white queen on (3, 3), every other square empty<br>• from: (3, 3) (white queen, not on an edge, nothing at all to block it)                                                                                                                                          | row 3: `(0, 3), (1, 3), (2, 3), (4, 3), (5, 3), (6, 3), (7, 3)` (7)<br>• column 3: `(3, 0), (3, 1), (3, 2), (3, 4), (3, 5), (3, 6), (3, 7)` (7)<br>• diagonal 1: `(0, 0), (1, 1), (2, 2), (4, 4), (5, 5), (6, 6), (7, 7)` (7)<br>• diagonal 2: `(0, 6), (1, 5), (2, 4), (4, 2), (5, 1), (6, 0)` (6) | :white_check_mark:          |
| M52<br><sub>getValidMoves_Queen_CornerTopLeft_returnTwentyOne</sub> | `getValidMoves()` | single white queen on (0, 0) (the top-left corner), every other square empty<br>• from: (0, 0) | row 0: `(1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0), (7, 0)` (7)<br>• column 0: `(0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (0, 6), (0, 7)` (7)<br>• diagonal: `(1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7)` (7) — 21 total | :white_check_mark: |
| M53<br><sub>getValidMoves_Queen_CornerTopRight_returnTwentyOne</sub> | `getValidMoves()` | single white queen on (7, 0) (the top-right corner), every other square empty<br>• from: (7, 0) | row 0: `(0, 0), (1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0)` (7)<br>• column 7: `(7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6), (7, 7)` (7)<br>• diagonal: `(6, 1), (5, 2), (4, 3), (3, 4), (2, 5), (1, 6), (0, 7)` (7) — 21 total | :white_check_mark: |
| M54<br><sub>getValidMoves_Queen_CornerBottomLeft_returnTwentyOne</sub> | `getValidMoves()` | single white queen on (0, 7) (the bottom-left corner), every other square empty<br>• from: (0, 7) | row 7: `(1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7), (7, 7)` (7)<br>• column 0: `(0, 0), (0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (0, 6)` (7)<br>• diagonal: `(1, 6), (2, 5), (3, 4), (4, 3), (5, 2), (6, 1), (7, 0)` (7) — 21 total | :x: |
| M55<br><sub>getValidMoves_Queen_CornerBottomRight_returnTwentyOne</sub> | `getValidMoves()` | single white queen on (7, 7) (the bottom-right corner), every other square empty<br>• from: (7, 7) | row 7: `(0, 7), (1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7)` (7)<br>• column 7: `(7, 0), (7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6)` (7)<br>• diagonal: `(6, 6), (5, 5), (4, 4), (3, 3), (2, 2), (1, 1), (0, 0)` (7) — 21 total | :x: |

Cases M52–M55 are the boundary values for `getValidMoves()`: one per corner, where the row, the column and one diagonal are each cut short and the fourth direction leaves the board immediately, so the queen returns 21 squares instead of the 27 it has in the middle of the board.

Cases M16, M17 and M23 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.
