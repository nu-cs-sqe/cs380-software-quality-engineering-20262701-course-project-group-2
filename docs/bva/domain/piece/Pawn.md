# BVA Analysis: `Pawn`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `Pawn` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `Pawn` object with the same color as the original piece.

| Test Case ID                                                      | Method(s) under test | State of the System | Expected Output                                             | Implemented? |
|-------------------------------------------------------------------|----------------------|---------------------|-------------------------------------------------------------|--------------|
| PC1<br><sub>makeCopy_WhitePawn_ReturnEquivalentDistinctPawn</sub> | `makeCopy()`         | WHITE pawn          | a distinct `Pawn` object with type `PAWN` and color `WHITE` | :X:          |
| PC2<br><sub>makeCopy_BlackPawn_ReturnEquivalentDistinctPawn</sub> | `makeCopy()`         | BLACK pawn          | a distinct `Pawn` object with type `PAWN` and color `BLACK` | :X:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the `Pawn`'s normal movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns the squares the `Pawn` can reach using only its own movement rules and the board boundaries: one square forward, plus a second square forward while `from` is still on the pawn's start row (row 6 for WHITE, row 1 for BLACK). The direction comes from the piece's own colour and location. 

| Test Case ID                                                            | Method(s) under test | State of the System                                                                                 | Expected Output                                                                                                                                    | Implemented? |
|-------------------------------------------------------------------------|----------------------|-----------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M1<br><sub>getValidMoves_Pawn_OnStartRow_returnTwo</sub>                | `getValidMoves()`    | a single white pawn on (4, 6), every other square empty<br>• from: (4, 6) (still on its start row)  | `{(4, 5), (4, 4)}`                                                                                                                                 | :X:          |
| M2<br><sub>getValidMoves_Pawn_OffStartRow_returnOne</sub>               | `getValidMoves()`    | a single white pawn on (4, 4), every other square empty<br>• from: (4, 4) (past its start row)      | `{(4, 3)}`                                                                                                                                         | :X:          |
| M18<br><sub>getValidMoves_Pawn_Black_returnTwo</sub>                    | `getValidMoves()`    | a single black pawn on (4, 1), every other square empty<br>• from: (4, 1) (still on its start row)  | `{(4, 2), (4, 3)}` — the mirror of M1: a black pawn advances towards row 7, so its destinations have a larger row than `from`, never a smaller one | :X:          |
| M44<br><sub>getValidMoves_Pawn_OneStepFromLastRank_returnLastRank</sub> | `getValidMoves()`    | a single white pawn on (4, 1), every other square empty<br>• from: (4, 1)                           | `{(4, 0)}` — reaching the last rank is a valid pawn destination; promotion behavior is handled separately                                          | :X:          |
| M68<br><sub>getValidMoves_Pawn_OneStepFromLastRankAtLeftEdge_returnOne</sub> | `getValidMoves()` | a single white pawn on (0, 1), every other square empty<br>• from: (0, 1) (one step from its end of the board, on the left edge) | `{(0, 0)}` — exactly one destination: the start-row double step does not apply here and nothing falls off the board | :white_check_mark: |
| M69<br><sub>getValidMoves_Pawn_OneStepFromLastRankAtRightEdge_returnOne</sub> | `getValidMoves()` | a single white pawn on (7, 1), every other square empty<br>• from: (7, 1) (one step from its end of the board, on the right edge) | `{(7, 0)}` — exactly one destination: the start-row double step does not apply here and nothing falls off the board | :white_check_mark: |
| M70<br><sub>getValidMoves_Pawn_BlackOneStepFromLastRankAtLeftEdge_returnOne</sub> | `getValidMoves()` | a single black pawn on (0, 6), every other square empty<br>• from: (0, 6) (one step from its end of the board, on the left edge) | `{(0, 7)}` — the black mirror of M68: one destination, in the black pawn's direction of travel | :white_check_mark: |
| M71<br><sub>getValidMoves_Pawn_BlackOneStepFromLastRankAtRightEdge_returnOne</sub> | `getValidMoves()` | a single black pawn on (7, 6), every other square empty<br>• from: (7, 6) (one step from its end of the board, on the right edge) | `{(7, 7)}` — the black mirror of M69: one destination, in the black pawn's direction of travel | :white_check_mark: |

Cases M68–M71 are the boundary values for `getValidMoves()`: a pawn never stands on the last rank because it promotes on arrival, so the corners themselves are unreachable and the real boundary is the last movable rank. Each case puts a pawn on an edge file, one step from its own end of the board, and expects exactly one destination — the two-square opening move must not fire away from the start row, and no destination may fall off the board.

Cases M3, M4, M5, M40, M41, M42 and M43 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.

This test is necessary to check that a black pawn is moving in the opposite direction as white pawns correctly.
