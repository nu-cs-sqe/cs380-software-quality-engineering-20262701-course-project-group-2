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

**Expected behavior:** Returns all movement-level / pseudo-legal destinations the `Rook` is allowed to make from its current square, considering board boundaries, blockers, and captures. It does not remove moves because of Game-level king-safety rules.

| Test Case ID                                                                | Method(s) under test | State of the System                                                                                                                                    | Expected Output                                                                                                       | Implemented? |
|-----------------------------------------------------------------------------|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|--------------|
| M6<br><sub>getValidMoves_Rook_returnEmpty</sub>                             | `getValidMoves()`    | standard starting board<br>• from: (0, 7) (white rook, own pawn on (0, 6) and own knight on (1, 7))                                                    | `{}`                                                                                                                  | :X:          |
| M7<br><sub>getValidMoves_Rook_AdjacentTilesUnblocked_returnAdjacent</sub>   | `getValidMoves()`    | after white (7, 6) -> (7, 4) and white knight (6, 7) -> (5, 5)<br>• from: (7, 7) (white rook)                                                          | `{(7, 6), (7, 5), (6, 7)}`                                                                                            | :X:          |
| M8<br><sub>getValidMoves_Rook_AllTilesUnblocked_returnAllRowAndColumn</sub> | `getValidMoves()`    | single white rook on (7, 7), every other square empty<br>• from: (7, 7)                                                                                | `{(0, 7), (1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7), (7, 0), (7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6)}`    | :X:          |
| M9<br><sub>getValidMoves_Rook_BlockedByOpponent_returnUpToOpponent</sub>    | `getValidMoves()`    | white rook on (7, 7)<br>• black piece on (7, 3)<br>• (7, 6), (7, 5), and (7, 4) empty<br>• own piece on (6, 7), so the horizontal direction is blocked | `{(7, 6), (7, 5), (7, 4), (7, 3)}` — the opponent's square is included as a capture and nothing beyond it is included | :X:          |
