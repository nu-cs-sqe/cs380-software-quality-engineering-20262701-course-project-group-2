# BVA Analysis for Game

### Method under test: `getCurrentTurn()`

Inputs:
- State Case: newly initialized game
- Current Turn Case: WHITE, BLACK
    - For initial game setup, only WHITE should be set.
    - BLACK becomes reachable once `switchTurn()` is called (see below) - moving a piece does not switch the turn by itself.

Output:
- `PieceColor` Case: WHITE, BLACK

| Test Case ID | Method(s) under test | State of the System      | Expected Output    | Implemented?       |
|--------------|----------------------|--------------------------|--------------------|--------------------|
| G1           | `getCurrentTurn()`   | Newly initialized `Game` | `PieceColor.WHITE` | :white_check_mark: |

### Method under test: `movePiece(Location from, Location to)`

- **G2: movePiece_Game_CallMade** ( :x: )
  - **State of the system**:
    - white pawn moved (4,2) -> (4,4)
  - **Expected output**: call made to Board.movePiece()

### Method under test: `switchTurn()`

- **G3: switchTurn_White_ReturnBlack** ( :x: )
  - **State of the system**:
    - newly initialized game
  - **Expected output**: `getCurrentTurn()` is `PieceColor.BLACK`
  
- **G4: switchTurn_Black_ReturnWhite** ( :x: )
  - **State of the system**:
    - after white has moved once and turn is currently black
  - **Expected output**: `getCurrentTurn()` is `PieceColor.White`

