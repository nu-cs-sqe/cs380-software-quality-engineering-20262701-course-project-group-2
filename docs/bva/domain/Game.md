# BVA Analysis for Game

### Method under test: `getCurrentTurn()`

Inputs:
- State Case: newly initialized game
- Current Turn Case: WHITE, BLACK
    - For initial game setup, only WHITE should be set.
    - BLACK becomes reachable once turn-switching behavior is implemented as part of piece movement.

Output:
- `PieceColor` Case: WHITE, BLACK

| Test Case ID | Method(s) under test | State of the System      | Expected Output    | Implemented?      |
|--------------|----------------------|--------------------------|--------------------|-------------------|
| G1           | `getCurrentTurn()`   | Newly initialized `Game` | `PieceColor.WHITE` | :white_checkmark: |

> Further BVA may be needed as features expand. This is preliminary only for the game setup feature.