# BVA Analysis: `King`

## Step 4: Test Cases

### Method under test: `makeCopy()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |

#### Method under test: `getValidMoves()`

- **M14: getValidMoves_King_BoxedIn_returnEmpty** ( :x: )
    - **State of the system**:
        - standard starting board
        - from: (4, 7) (white king, all five in-board neighbours hold own pieces)
    - **Expected output**: `{}` — size 0 on the standard board

- **M15: getValidMoves_King_OneNeighbourFreed_returnOne** ( :x: )
    - **State of the system**:
        - after white (4, 6) -> (4, 4)
        - from: (4, 7) (white king; (4, 6) now empty, (4, 5) also empty)
    - **Expected output**: `{(4, 6)}`

- **M21: getValidMoves_King_OpponentAdjacent_returnIncludingOpponent** ( :x: )
    - **State of the system**:
        - after black (5, 1) -> (5, 3), black (5, 3) -> (5, 4), black (5, 4) -> (5, 5), black (5, 5) captures white (4, 6)
        - from: (4, 7) (white king; (4, 6) now holds a black pawn, (3, 6) and (5, 6) hold own pawns, (3, 7) and (5, 7) hold own bishops)
    - **Expected output**: `{(4, 6)}`

- **M22: getValidMoves_King_AllDirectionsFromCentre_returnEight** ( :x: )
    - **State of the system**:
        - single white king on (3, 3), every other square empty
        - from: (3, 3) (white king, not on an edge, nothing at all to block it)
    - **Expected output**: `{(2, 2), (2, 3), (2, 4), (3, 2), (3, 4), (4, 2), (4, 3), (4, 4)}`

- **M26: getValidMoves_King_RightCastlePathClear_returnIncludingCastle** ( :x: )
    - **State of the system**:
        - neither rook nor king has been moved yet
        - after white (4, 6) -> (4, 4), black (7, 1) -> (7, 3), white (5, 7) -> (4, 6) (bishop off f1), black (7, 3) -> (7, 4), white (6, 7) -> (5, 5) (knight off g1)
        - from: (4, 7) (white king; f1 (5, 7) and g1 (6, 7) are empty, own rook still on h1 (7, 7), own pieces still on d1 (3, 7), d2 (3, 6), e2 (4, 6) and f2 (5, 6))
    - **Expected output**: `{(5, 7), (6, 7)}`

- **M27: getValidMoves_King_LeftCastlePathClear_returnIncludingCastle** ( :x: )
    - **State of the system**:
        - neither rook nor king has been moved yet
        - after white (3, 6) -> (3, 4) (pawn off d2), black (6, 1) -> (6, 3), white (1, 6) -> (1, 4) (pawn off b2), black (6, 3) -> (6, 4), white (2, 7) -> (0, 5) (bishop off c1), black (6, 4) -> (6, 5), white (1, 7) -> (2, 5) (knight off b1), black (5, 1) -> (5, 3), white (3, 7) -> (3, 6) (queen off d1), black (5, 3) -> (5, 4)
        - from: (4, 7) (white king; b1 (1, 7), c1 (2, 7) and d1 (3, 7) are empty, own rook still on a1 (0, 7), own bishop still on f1 (5, 7))
    - **Expected output**: `{(3, 7), (2, 7)}`

- **M28: getValidMoves_King_RightCastlePathClear_KingMoved_NoCastle** ( :x: )
    - **State of the system**:
        - king has been moved
        - same setup as M26 but king has also moved one square to right
    - **Expected output**: `{(5, 7)}`

- **M29: getValidMoves_King_RightCastlePathClear_RookMoved_NoCastle** ( :x: )
    - **State of the system**:
        - rook has been moved
        - same setup as M26 but rook has also moved one square to left
    - **Expected output**: `{(5, 7)}`

  