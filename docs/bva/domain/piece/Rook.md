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

**Expected behavior:** Returns every square on the row and the column through `from`, stopping only at the board edge. 
| Test Case ID                                                                | Method(s) under test | State of the System                                                                                                                                    | Expected Output                                                                                                       | Implemented? |
|-----------------------------------------------------------------------------|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|--------------|
| M8<br><sub>getValidMoves_Rook_AllTilesUnblocked_returnAllRowAndColumn</sub> | `getValidMoves()`    | single white rook on (7, 7), every other square empty<br>• from: (7, 7)                                                                                | `{(0, 7), (1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7), (7, 0), (7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6)}`    | :white_check_mark:          |
| M48<br><sub>getValidMoves_Rook_CornerTopLeft_returnFourteen</sub> | `getValidMoves()` | single white rook on (0, 0) (the top-left corner), every other square empty<br>• from: (0, 0) | row 0: `(1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0), (7, 0)` (7)<br>• column 0: `(0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (0, 6), (0, 7)` (7) — 14 total | :white_check_mark: | 
| M49<br><sub>getValidMoves_Rook_CornerTopRight_returnFourteen</sub> | `getValidMoves()` | single white rook on (7, 0) (the top-right corner), every other square empty<br>• from: (7, 0) | row 0: `(0, 0), (1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0)` (7)<br>• column 7: `(7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6), (7, 7)` (7) — 14 total | :white_check_mark: |
| M50<br><sub>getValidMoves_Rook_CornerBottomLeft_returnFourteen</sub> | `getValidMoves()` | single white rook on (0, 7) (the bottom-left corner), every other square empty<br>• from: (0, 7) | row 7: `(1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7), (7, 7)` (7)<br>• column 0: `(0, 0), (0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (0, 6)` (7) — 14 total | :white_check_mark: |
| M51<br><sub>getValidMoves_Rook_CornerBottomRight_returnFourteen</sub> | `getValidMoves()` | single white rook on (7, 7) (the bottom-right corner), every other square empty<br>• from: (7, 7) | row 7: `(0, 7), (1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7)` (7)<br>• column 7: `(7, 0), (7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6)` (7) — 14 total | :x: |

Cases M48–M51 are the boundary values for `getValidMoves()`: one per corner, the extreme positions where two of the four sweeps leave the board immediately and the other two run the whole length of an edge. M8 already stands on the (7, 7) corner; M51 is kept so the corner series is complete and symmetric.

Cases M6, M7 and M9 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.
