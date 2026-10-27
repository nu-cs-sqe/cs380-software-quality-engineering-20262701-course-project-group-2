# BVA Analysis for Board

### Method under test: `getSnapshot()`

Inputs:
- State Collection: newly initialized chess board
    - The current public interface only allows the standard initialized board state.
    - Empty, exactly one piece, and other board states cannot currently be set.

Outputs:
- Board Collection: fixed 8x8 multidimensional array
- Array Indices: [0, 7]
    - Smallest valid row/column: 0
    - Largest valid row/column: 7
    - -1 and 8 cannot be set through `getSnapshot()`
- Occupied Square Count: 32
- Piece Color Cases: BLACK, WHITE
- Square Content Cases: Piece, empty (`null`)

| Test Case ID | Method(s) under test | State of the System       | Expected Output                                                                                                        | Implemented?       |
|--------------|----------------------|---------------------------|------------------------------------------------------------------------------------------------------------------------|--------------------|
| B1           | `getSnapshot()`      | Newly initialized `Board` | Snapshot contains exactly 8 rows and each row contains exactly 8 columns                                               | :white_check_mark: |
| B2           | `getSnapshot()`      | Newly initialized `Board` | Boundary positions are initialized correctly: `[0][0]` contains a Black Rook and `[7][7]` contains a White Rook        | :white_check_mark: |
| B3           | `getSnapshot()`      | Newly initialized `Board` | Snapshot contains exactly 32 occupied squares: 16 Black pieces and 16 White pieces; the remaining 32 squares are empty | :white_check_mark: |
| B4           | `getSnapshot()`      | Newly initialized `Board` | All 32 pieces are in their standard starting chess positions                                                           | :x:                |

> Further BVA may be needed as features expand. This is preliminary only for the game setup feature.