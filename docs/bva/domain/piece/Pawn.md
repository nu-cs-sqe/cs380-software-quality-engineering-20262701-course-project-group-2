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

**Expected behavior:** Returns the squares the `Pawn` can reach using only its own movement rules and the board boundaries: one square forward, plus a second square forward while `from` is still on the pawn's start row (row 6 for WHITE, row 1 for BLACK). The direction comes from the piece's own colour and location. No other square on the board is read, so the result never stops at a blocker and never includes the diagonal capture squares — blockers, captures, promotion, king safety and castling are decided by [`Game.getLegalMoves()`](../Game.md).

| Test Case ID                                                            | Method(s) under test | State of the System                                                                                 | Expected Output                                                                                                                                    | Implemented? |
|-------------------------------------------------------------------------|----------------------|-----------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M1<br><sub>getValidMoves_Pawn_OnStartRow_returnTwo</sub>                | `getValidMoves()`    | a single white pawn on (4, 6), every other square empty<br>• from: (4, 6) (still on its start row)  | `{(4, 5), (4, 4)}`                                                                                                                                 | :X:          |
| M2<br><sub>getValidMoves_Pawn_OffStartRow_returnOne</sub>               | `getValidMoves()`    | a single white pawn on (4, 4), every other square empty<br>• from: (4, 4) (past its start row)      | `{(4, 3)}`                                                                                                                                         | :X:          |
| M18<br><sub>getValidMoves_Pawn_Black_returnTwo</sub>                    | `getValidMoves()`    | a single black pawn on (4, 1), every other square empty<br>• from: (4, 1) (still on its start row)  | `{(4, 2), (4, 3)}` — the mirror of M1: a black pawn advances towards row 7, so its destinations have a larger row than `from`, never a smaller one | :X:          |
| M44<br><sub>getValidMoves_Pawn_OneStepFromLastRank_returnLastRank</sub> | `getValidMoves()`    | a single white pawn on (4, 1), every other square empty<br>• from: (4, 1)                           | `{(4, 0)}` — reaching the last rank is a valid pawn destination; promotion behavior is handled separately                                          | :X:          |

Cases M3, M4, M5, M40, M41, M42 and M43 were moved to [`Game.getLegalMoves()`](../Game.md) with their IDs unchanged: each of them depended on another piece on the board, which `getValidMoves()` no longer looks at.

This test is necessary to check that a black pawn is moving in the opposite direction as white pawns correctly.
