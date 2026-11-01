# BVA Analysis: `King`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `King` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `King` object with the same color as the original piece.

| Test Case ID                                                      | Method(s) under test | State of the System | Expected Output                                             | Implemented? |
|-------------------------------------------------------------------|----------------------|---------------------|-------------------------------------------------------------|--------------|
| KC1<br><sub>makeCopy_WhiteKing_ReturnEquivalentDistinctKing</sub> | `makeCopy()`         | WHITE king          | a distinct `King` object with type `KING` and color `WHITE` | :X:          |
| KC2<br><sub>makeCopy_BlackKing_ReturnEquivalentDistinctKing</sub> | `makeCopy()`         | BLACK king          | a distinct `King` object with type `KING` and color `BLACK` | :X:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the king's normal one-square movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns all normal one-square movement-level / pseudo-legal destinations the `King` is allowed to make from its current square, considering board boundaries, blockers, and captures. It does not remove moves because of Game-level king-safety rules.

| Test Case ID                                                                  | Method(s) under test | State of the System                                                                                                                                                                                           | Expected Output                                                    | Implemented? |
|-------------------------------------------------------------------------------|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------|--------------|
| M14<br><sub>getValidMoves_King_BoxedIn_returnEmpty</sub>                      | `getValidMoves()`    | standard starting board<br>• from: (4, 7) (white king, all five in-board neighbours hold own pieces)                                                                                                          | `{}` — size 0 on the standard board                                | :X:          |
| M15<br><sub>getValidMoves_King_OneNeighbourFreed_returnOne</sub>              | `getValidMoves()`    | after white (4, 6) -> (4, 4)<br>• from: (4, 7) (white king; (4, 6) now empty)                                                                                                                                 | `{(4, 6)}`                                                         | :X:          |
| M21<br><sub>getValidMoves_King_OpponentAdjacent_returnIncludingOpponent</sub> | `getValidMoves()`    | after black (5, 1) -> (5, 3), black (5, 3) -> (5, 4), black (5, 4) -> (5, 5), black (5, 5) captures white (4, 6)<br>• from: (4, 7) (white king; (4, 6) now holds a black pawn, surrounding own pieces remain) | `{(4, 6)}`                                                         | :X:          |
| M22<br><sub>getValidMoves_King_AllDirectionsFromCentre_returnEight</sub>      | `getValidMoves()`    | single white king on (3, 3), every other square empty<br>• from: (3, 3) (white king, not on an edge, nothing to block it)                                                                                     | `{(2, 2), (2, 3), (2, 4), (3, 2), (3, 4), (4, 2), (4, 3), (4, 4)}` | :X:          |
| M46<br><sub>getValidMoves_King_CornerOpen_returnThree</sub>                   | `getValidMoves()`    | single white king on (0, 0), every other square empty<br>• from: (0, 0)                                                                                                                                       | `{(0, 1), (1, 0), (1, 1)}` — no off-board locations are returned   | :X:          |

