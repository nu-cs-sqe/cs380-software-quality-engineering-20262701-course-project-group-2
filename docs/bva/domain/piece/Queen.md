# BVA Analysis: `Queen`

## Step 4: Test Cases

### Method under test: `makeCopy()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |

#### Method under test: `getValidMoves()`

- **M16: getValidMoves_Queen_BoxedIn_returnEmpty** ( :x: )
    - **State of the system**:
        - standard starting board
        - from: (3, 7) (white queen, own pieces on (3, 6), (2, 6), (4, 6), (2, 7) and (4, 7))
    - **Expected output**: `{}` — every in-board direction is blocked by a friendly piece

- **M17: getValidMoves_Queen_AdjacentsCleared_returnAdjacent** ( :x: )
    - **State of the system**:
        - after white (3, 6) -> (3, 4)
        - from: (3, 7) (white queen)
    - **Expected output**: `{(3, 6), (3, 5)}` exactly — the cleared file up to the own pawn on (3, 4); the diagonals are still blocked by own pawns, so the queen's combined sliding directions add nothing here

- **M23: getValidMoves_Queen_OpponentInFile_returnUpToOpponent** ( :x: )
    - **State of the system**:
        - after black (4, 1) -> (4, 3), black (4, 3) -> (4, 4), black (4, 4) -> (4, 5), white (3, 6) captures (4, 5), black (3, 1) -> (3, 3)
        - from: (3, 7) (white queen; the file below it is empty down to the black pawn on (3, 3), and (2, 6) and (4, 6) still hold own pawns)
    - **Expected output**: `{(3, 6), (3, 5), (3, 4), (3, 3)}`

- **M24: getValidMoves_Queen_AllDirectionsFromCentre_returnTwentySeven** ( :x: )
    - **State of the system**:
        - single white queen on (3, 3), every other square empty
        - from: (3, 3) (white queen, not on an edge, nothing at all to block it)
    - **Expected output**:
        - row 3: `(0, 3), (1, 3), (2, 3), (4, 3), (5, 3), (6, 3), (7, 3)` (7)
        - column 3: `(3, 0), (3, 1), (3, 2), (3, 4), (3, 5), (3, 6), (3, 7)` (7)
        - diagonal 1: `(0, 0), (1, 1), (2, 2), (4, 4), (5, 5), (6, 6), (7, 7)` (7)
        - diagonal 2: `(0, 6), (1, 5), (2, 4), (4, 2), (5, 1), (6, 0)` (6)
 
    