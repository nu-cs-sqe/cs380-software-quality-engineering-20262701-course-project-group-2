# BVA Analysis: `Bishop`

## Step 4: Test Cases

### Method under test: `makeCopy()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |


### Method under test: `getValidMoves()`

- **M10: getValidMoves_Bishop_Blocked_returnEmpty** ( :x: )
    - **State of the system**:
        - standard starting board
        - from: (2, 7) (white bishop, own pawns on (1, 6) and (3, 6))
    - **Expected output**: `{}`

- **M11: getValidMoves_Bishop_OpenDiagonal_returnDiagonal** ( :x: )
    - **State of the system**:
        - after white (3, 6) -> (3, 4)
        - from: (2, 7) (white bishop)
    - **Expected output**: `{(3, 6), (4, 5), (5, 4), (6, 3), (7, 2)}`

- **M12: getValidMoves_Bishop_OpponentInDiagonal_returnUpToOpponent** ( :x: )
    - **State of the system**:
        - after white (3, 6) -> (3, 4), black (4, 1) -> (4, 3), black (4, 3) -> (4, 4), black (4, 4) -> (4, 5)
        - from: (2, 7) (white bishop; its diagonal now runs through the empty (3, 6) into the black pawn on (4, 5))
    - **Expected output**: `{(3, 6), (4, 5)}` — the same shape as M9: the empty square before the opponent is included, the opponent's own square is included (it is a capture), and nothing behind it is, so `(5, 4)`, `(6, 3)` and `(7, 2)` are excluded. Stops at the opponent rather than at the board edge, which is what separates this from M11.

