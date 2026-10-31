# BVA Analysis: `Rook`

## Step 4: Test Cases

### Method under test: `makeCopy()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |

#### Method under test: `getValidMoves()`


- **M6: getValidMoves_Rook_returnEmpty** ( :x: )
    - **State of the system**:
        - standard starting board
        - from: (0, 7) (white rook, own pawn on (0, 6) and own knight on (1, 7))
    - **Expected output**: `{}`

- **M7: getValidMoves_Rook_AdjacentTilesUnblocked_returnAdjacent** ( :x: )
    - **State of the system**:
        - after white (7, 6) -> (7, 4) and white knight (6, 7) -> (5, 5)
        - from: (7, 7) (white rook)
    - **Expected output**: `{(7, 6), (7, 5), (6, 7)}`

- **M8: getValidMoves_Rook_AllTilesUnblocked_returnAllRowAndColumn** ( :x: )
    - **State of the system**:
        - just the rook on (7, 7) (white rook) i know this isn't possible
    - **Expected output**: `{(x, x) for x in range(0,7)}` sorry if this isn't valid output format

- **M9: getValidMoves_Rook_BlockedByOpponent_returnUpToOpponent** ( :x: )
    - **State of the system**:
        - after black (7, 1) -> (7, 3), white (7, 6) -> (7, 5), black pawn (7,2)->(7,4), white (7, 5) -> (7, 4)
        - from: (7, 7) (white rook, black pawn on (7, 3))
    - **Expected output**: `{(7, 6), (7, 5), (7, 4), (7, 3)}`
