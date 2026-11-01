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

**Expected behavior:** Returns all movement-level / pseudo-legal destinations the `Pawn` is allowed to make from its current square, considering board boundaries, blockers, and captures. It does not remove moves because of Game-level king-safety rules.

| Test Case ID                                                            | Method(s) under test | State of the System                                                                                            | Expected Output                                                                                                                                    | Implemented? |
|-------------------------------------------------------------------------|----------------------|----------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M1<br><sub>getValidMoves_Pawn_OnStartRow_returnTwo</sub>                | `getValidMoves()`    | standard starting board<br>• from: (4, 6) (white pawn, still on its start row, both adjacent diagonals empty)  | `{(4, 5), (4, 4)}`                                                                                                                                 | :X:          |
| M2<br><sub>getValidMoves_Pawn_OffStartRow_returnOne</sub>               | `getValidMoves()`    | after M1's white move (4, 6) -> (4, 4)<br>• from: (4, 4) (white pawn, past its start row; (4, 3) empty)        | `{(4, 3)}`                                                                                                                                         | :X:          |
| M3<br><sub>getValidMoves_Pawn_BlockedAhead_returnEmpty</sub>            | `getValidMoves()`    | after white (4, 6) -> (4, 4) and black (4, 1) -> (4, 3)<br>• from: (4, 4) (white pawn)                         | `{}`                                                                                                                                               | :X:          |
| M4<br><sub>getValidMoves_Pawn_OneCapturePossible_returnTwo</sub>        | `getValidMoves()`    | after white (4, 6) -> (4, 4) and black (5, 1) -> (5, 3)<br>• from: (4, 4) (white pawn)                         | `{(4, 3), (5, 3)}`                                                                                                                                 | :X:          |
| M5<br><sub>getValidMoves_Pawn_TwoCapturesPossible_returnThree</sub>     | `getValidMoves()`    | after white (4, 6) -> (4, 4), black (3, 1) -> (3, 3) and black (5, 1) -> (5, 3)<br>• from: (4, 4) (white pawn) | `{(4, 3), (3, 3), (5, 3)}`                                                                                                                         | :X:          |
| M18<br><sub>getValidMoves_Pawn_Black_returnTwo</sub>                    | `getValidMoves()`    | standard starting board<br>• from: (4, 1) (black pawn, still on its start row, both adjacent diagonals empty)  | `{(4, 2), (4, 3)}` — the mirror of M1: a black pawn advances towards row 7, so its destinations have a larger row than `from`, never a smaller one | :X:          |
| M40<br><sub>getValidMoves_Pawn_OnStartRowBlockedAhead_returnEmpty</sub> | `getValidMoves()`    | white pawn on (4, 6)<br>• an occupied square on (4, 5)<br>• (4, 4) empty                                       | `{}` — the pawn cannot move one square forward and cannot jump over the blocker to make the two-square opening move                                | :X:          |
| M41<br><sub>getValidMoves_Pawn_OwnPieceDiagonal_NotCapture</sub>        | `getValidMoves()`    | white pawn on (4, 4)<br>• (4, 3) empty<br>• own piece on (5, 3)                                                | `{(4, 3)}` — the own piece on the diagonal is not a valid capture destination                                                                      | :X:          |
| M42<br><sub>getValidMoves_Pawn_OpponentDirectlyAhead_NotCapture</sub>   | `getValidMoves()`    | white pawn on (4, 4)<br>• opponent piece on (4, 3)<br>• diagonal squares empty                                 | `{}` — a pawn cannot capture a piece directly in front of it                                                                                       | :X:          |
| M43<br><sub>getValidMoves_Pawn_OnLeftEdge_NoOffBoardDiagonal</sub>      | `getValidMoves()`    | white pawn on (0, 4)<br>• (0, 3) empty<br>• opponent piece on (1, 3)                                           | `{(0, 3), (1, 3)}` — no destination with column -1 is returned                                                                                     | :X:          |
| M44<br><sub>getValidMoves_Pawn_OneStepFromLastRank_returnLastRank</sub> | `getValidMoves()`    | white pawn on (4, 1)<br>• (4, 0) empty                                                                         | `{(4, 0)}` — reaching the last rank is a valid pawn destination; promotion behavior is handled separately                                          | :X:          |

This test is necessary to check that a black pawn is moving in the opposite direction as white pawns correctly.
