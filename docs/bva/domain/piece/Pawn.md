# BVA Analysis: `Pawn`

## Step 4: Test Cases

### Method under test: `makeCopy()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |

#### Method under test: `getValidMoves()`

- **M1: getValidMoves_Pawn_OnStartRow_returnTwo** ( :x: )
    - **State of the system**:
        - standard starting board
        - from: (4, 6) (white pawn, still on its start row, both adjacent diagonals empty)
    - **Expected output**: `{(4, 5), (4, 4)}`

- **M2: getValidMoves_Pawn_OffStartRow_returnOne** ( :x: )
    - **State of the system**:
        - after M1's white move (4, 6) -> (4, 4)
        - from: (4, 4) (white pawn, past its start row; (4, 3) empty)
    - **Expected output**: `{(4, 3)}`

- **M3: getValidMoves_Pawn_BlockedAhead_returnEmpty** ( :x: )
    - **State of the system**:
        - after white (4, 6) -> (4, 4) and black (4, 1) -> (4, 3)
        - from: (4, 4) (white pawn)
    - **Expected output**: `{}`

- **M4: getValidMoves_Pawn_OneCapturePossible_returnTwo** ( :x: )
    - **State of the system**:
        - after white (4, 6) -> (4, 4) and black (5, 1) -> (5, 3)
        - from: (4, 4) (white pawn)
    - **Expected output**: `{(4, 3), (5, 3)}`

- **M5: getValidMoves_Pawn_TwoCapturesPossible_returnThree** ( :x: )
    - **State of the system**:
        - after white (4, 6) -> (4, 4), black (3, 1) -> (3, 3) and black (5, 1) -> (5, 3)
        - from: (4, 4) (white pawn)
    - **Expected output**: `{(4, 3), (3, 3), (5, 3)}`

this test is necessary to check that a black pawn is moving in the opposite direction as white pawns correctly
- **M18: getValidMoves_Pawn_Black_returnTwo** ( :x: )
    - **State of the system**:
        - standard starting board
        - from: (4, 1) (black pawn, still on its start row, both adjacent diagonals empty)
    - **Expected output**: `{(4, 2), (4, 3)}` — the mirror of M1: a black pawn advances towards row 7, so its destinations have a larger row than `from`, never a smaller one. The empty set, and a set containing `(4, 0)`, both fail.
