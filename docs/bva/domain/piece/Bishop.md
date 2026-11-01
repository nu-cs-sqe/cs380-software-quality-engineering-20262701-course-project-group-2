# BVA Analysis: `Bishop`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `Bishop` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `Bishop` object with the same color as the original piece.

| Test Case ID                                                          | Method(s) under test | State of the System | Expected Output                                                 | Implemented? |
|-----------------------------------------------------------------------|----------------------|---------------------|-----------------------------------------------------------------|--------------|
| BC1<br><sub>makeCopy_WhiteBishop_ReturnEquivalentDistinctBishop</sub> | `makeCopy()`         | WHITE bishop        | a distinct `Bishop` object with type `BISHOP` and color `WHITE` | :X:          |
| BC2<br><sub>makeCopy_BlackBishop_ReturnEquivalentDistinctBishop</sub> | `makeCopy()`         | BLACK bishop        | a distinct `Bishop` object with type `BISHOP` and color `BLACK` | :X:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the `Bishop`'s normal movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns all movement-level / pseudo-legal destinations the `Bishop` is allowed to make from its current square, considering board boundaries, blockers, and captures. It does not remove moves because of Game-level king-safety rules.

| Test Case ID                                                                  | Method(s) under test | State of the System                                                                                                                                                                                                 | Expected Output                                                                                                                                          | Implemented? |
|-------------------------------------------------------------------------------|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M10<br><sub>getValidMoves_Bishop_Blocked_returnEmpty</sub>                    | `getValidMoves()`    | standard starting board<br>• from: (2, 7) (white bishop, own pawns on (1, 6) and (3, 6))                                                                                                                            | `{}`                                                                                                                                                     | :X:          |
| M11<br><sub>getValidMoves_Bishop_OpenDiagonal_returnDiagonal</sub>            | `getValidMoves()`    | after white (3, 6) -> (3, 4)<br>• from: (2, 7) (white bishop)                                                                                                                                                       | `{(3, 6), (4, 5), (5, 4), (6, 3), (7, 2)}`                                                                                                               | :X:          |
| M12<br><sub>getValidMoves_Bishop_OpponentInDiagonal_returnUpToOpponent</sub>  | `getValidMoves()`    | after white (3, 6) -> (3, 4), black (4, 1) -> (4, 3), black (4, 3) -> (4, 4), black (4, 4) -> (4, 5)<br>• from: (2, 7) (white bishop; its diagonal now runs through the empty (3, 6) into the black pawn on (4, 5)) | `{(3, 6), (4, 5)}` — the empty square before the opponent is included, the opponent's square is included as a capture, and nothing behind it is included | :X:          |
| M45<br><sub>getValidMoves_Bishop_AllDirectionsFromCentre_returnThirteen</sub> | `getValidMoves()`    | single white bishop on (3, 3), every other square empty<br>• from: (3, 3)                                                                                                                                           | `{(0, 0), (1, 1), (2, 2), (4, 4), (5, 5), (6, 6), (7, 7), (0, 6), (1, 5), (2, 4), (4, 2), (5, 1), (6, 0)}`                                               | :X:          |
