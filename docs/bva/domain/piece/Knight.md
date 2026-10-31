# BVA Analysis: `Knight`

## Step 4: Test Cases

### Method under test: `makeCopy()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |

#### Method under test: `getValidMoves()`

- **M13: getValidMoves_Knight_PathBlocked_returnTwo** ( :x: )
    - **State of the system**:
        - standard starting board, knight surrounded by its own pawn and rook
        - from: (1, 7) (white knight)
    - **Expected output**: `{(0, 5), (2, 5)}` exactly — (3, 6) holds an own pawn and is excluded, (1, 5) is a straight line and is excluded, and the two included squares are reached through blockers

- **M19: getValidMoves_Knight_NoValidDestinations_returnEmpty** ( :x: )
    - **State of the system**:
        - a white knight on (0, 0), a white rook on (1, 2) and a white bishop on (2, 1), every other square empty
        - from: (0, 0) white knight in the corner
    - **Expected output**: `{}`

- **M20: getValidMoves_Knight_OpponentOnDestination_returnIncludingOpponent** ( :x: )
    - **State of the system**:
        - after white (3, 6) -> (3, 4), black (0, 1) -> (0, 3), black (0, 3) -> (0, 4), black (0, 4) -> (0, 5)
        - from: (1, 7) (white knight; (0, 5) holds a black pawn, (2, 5) and (3, 6) are empty)
    - **Expected output**: `{(0, 5), (2, 5), (3, 6)}`

- **M25: getValidMoves_Knight_AllDirectionsFromCentre_returnEight** ( :x: )
    - **State of the system**:
        - a single white knight on (3, 3), every other square empty
        - from: (3, 3)
    - **Expected output**: `{(1, 2), (1, 4), (2, 1), (2, 5), (4, 1), (4, 5), (5, 2), (5, 4)}` 
