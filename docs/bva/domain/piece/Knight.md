# BVA Analysis: `Knight`

## Step 4: Test Cases

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent `Knight` for board snapshots or simulated board states.

**Expected behavior:** Returns a distinct `Knight` object with the same color as the original piece.

| Test Case ID                                                          | Method(s) under test | State of the System | Expected Output                                                 | Implemented? |
|-----------------------------------------------------------------------|----------------------|---------------------|-----------------------------------------------------------------|--------------|
| NC1<br><sub>makeCopy_WhiteKnight_ReturnEquivalentDistinctKnight</sub> | `makeCopy()`         | WHITE knight        | a distinct `Knight` object with type `KNIGHT` and color `WHITE` | :X:          |
| NC2<br><sub>makeCopy_BlackKnight_ReturnEquivalentDistinctKnight</sub> | `makeCopy()`         | BLACK knight        | a distinct `Knight` object with type `KNIGHT` and color `BLACK` | :X:          |

### Method under test: `getValidMoves()`

**Purpose / use:** Supplies the `Knight`'s normal movement options to Board/Game before Game-level legality filters are applied.

**Expected behavior:** Returns all movement-level / pseudo-legal destinations the `Knight` is allowed to make from its current square, considering board boundaries, blockers, and captures. It does not remove moves because of Game-level king-safety rules.

| Test Case ID                                                                         | Method(s) under test | State of the System                                                                                                                                                                           | Expected Output                                                                                                                                                                | Implemented? |
|--------------------------------------------------------------------------------------|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M13<br><sub>getValidMoves_Knight_PathBlocked_returnTwo</sub>                         | `getValidMoves()`    | standard starting board, knight surrounded by its own pawn and rook<br>• from: (1, 7) (white knight)                                                                                          | `{(0, 5), (2, 5)}` exactly — (3, 6) holds an own pawn and is excluded, and the two included squares are reached even though pieces are between the knight and the destinations | :X:          |
| M19<br><sub>getValidMoves_Knight_NoValidDestinations_returnEmpty</sub>               | `getValidMoves()`    | a white knight on (0, 0), a white rook on (1, 2) and a white bishop on (2, 1), every other square empty<br>• from: (0, 0) white knight in the corner                                          | `{}`                                                                                                                                                                           | :X:          |
| M20<br><sub>getValidMoves_Knight_OpponentOnDestination_returnIncludingOpponent</sub> | `getValidMoves()`    | after white (3, 6) -> (3, 4), black (0, 1) -> (0, 3), black (0, 3) -> (0, 4), black (0, 4) -> (0, 5)<br>• from: (1, 7) (white knight; (0, 5) holds a black pawn, (2, 5) and (3, 6) are empty) | `{(0, 5), (2, 5), (3, 6)}`                                                                                                                                                     | :X:          |
| M25<br><sub>getValidMoves_Knight_AllDirectionsFromCentre_returnEight</sub>           | `getValidMoves()`    | a single white knight on (3, 3), every other square empty<br>• from: (3, 3)                                                                                                                   | `{(1, 2), (1, 4), (2, 1), (2, 5), (4, 1), (4, 5), (5, 2), (5, 4)}`                                                                                                             | :X:          |
