# BVA Analysis: `Rook`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `Rook` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `Rook` object with the same color as the original piece.

| Test Case ID                                                      | Method(s) under test | State of the System | Expected Output                                             | Implemented?       |
|-------------------------------------------------------------------|----------------------|---------------------|-------------------------------------------------------------|--------------------|
| RC1<br><sub>makeCopy_WhiteRook_ReturnEquivalentDistinctRook</sub> | `makeCopy()`         | WHITE rook          | a distinct `Rook` object with type `ROOK` and color `WHITE` | :white_check_mark: |
| RC2<br><sub>makeCopy_BlackRook_ReturnEquivalentDistinctRook</sub> | `makeCopy()`         | BLACK rook          | a distinct `Rook` object with type `ROOK` and color `BLACK` | :white_check_mark: |

### Method under test: `getValidMoves(Location from)`

**Purpose / use:** Generates the Rook's geometric row/column destinations before Game applies blockers and captures.

**Expected behavior:** Returns every square on the row and the column through `from`, stopping only at the board edge.

| Test Case ID                                                          | Method(s) under test           | State of the System                                                                               | Expected Output                                                                                                                                            | Implemented?       |
|-----------------------------------------------------------------------|--------------------------------|---------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| M8<br><sub>getValidMoves_Rook_InteriorUnblocked_ReturnFourteen</sub>  | `getValidMoves(Location from)` | single WHITE rook on `(3,3)`, every other square empty                                            | row 3: `(0,3), (1,3), (2,3), (4,3), (5,3), (6,3), (7,3)`; column 3: `(3,0), (3,1), (3,2), (3,4), (3,5), (3,6), (3,7)`                                      | :white_check_mark: |
| M48<br><sub>getValidMoves_Rook_CornerTopLeft_returnFourteen</sub>     | `getValidMoves(Location from)` | single white rook on (0, 0) (the top-left corner), every other square empty<br>• from: (0, 0)     | row 0: `(1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0), (7, 0)` (7)<br>• column 0: `(0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (0, 6), (0, 7)` (7) — 14 total | :white_check_mark: | 
| M49<br><sub>getValidMoves_Rook_CornerTopRight_returnFourteen</sub>    | `getValidMoves(Location from)` | single white rook on (7, 0) (the top-right corner), every other square empty<br>• from: (7, 0)    | row 0: `(0, 0), (1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0)` (7)<br>• column 7: `(7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6), (7, 7)` (7) — 14 total | :white_check_mark: |
| M50<br><sub>getValidMoves_Rook_CornerBottomLeft_returnFourteen</sub>  | `getValidMoves(Location from)` | single white rook on (0, 7) (the bottom-left corner), every other square empty<br>• from: (0, 7)  | row 7: `(1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7), (7, 7)` (7)<br>• column 0: `(0, 0), (0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (0, 6)` (7) — 14 total | :white_check_mark: |
| M51<br><sub>getValidMoves_Rook_CornerBottomRight_returnFourteen</sub> | `getValidMoves(Location from)` | single white rook on (7, 7) (the bottom-right corner), every other square empty<br>• from: (7, 7) | row 7: `(0, 7), (1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7)` (7)<br>• column 7: `(7, 0), (7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6)` (7) — 14 total | :white_check_mark: |

M8 is the interior representative. Cases M48–M51 cover each corner, where two sweeps leave the board immediately and the other two run the full edge.

Cases M6, M7 and M9 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves(Location)` does not inspect.
