# BVA Analysis: `BoardController`

### Method under test: `handleSquareClick(Location location)`
1. Nothing selected + click a piece of the active player's colour -> that square becomes selected and its valid destinations are highlighted
2. Piece selected + click a highlighted destination -> the move is performed, and the selection and highlight are cleared
3. Piece selected + click anything else on the board -> no move and the selection is cleared

### Step 1-3: Analysis
Inputs:
- Selection state: cases {nothing selected, own piece selected}
- Clicked square content: cases {own piece, opponent piece, empty square}
- Clicked square relative to the highlighted set: cases {inside the set, outside the set}
- Clicked square relative to the selected square: cases {same square, different square}
- Coordinate: interval [0,7] for column and row
- Current turn: cases {WHITE, BLACK}

Outputs:

- Selected square: cases {`null`, a `Location`}
- Highlighted set: cases {empty, exactly 1 square, 2 or more squares}
- Board snapshot: cases {unchanged, one piece relocated, one piece captured} (capture count drops from 32 to 31)
- Current turn: cases {unchanged, switched}

### Step 4: Test Cases

#### Nothing selected

- **C1: handleSquareClick_selectOwnPawn_NoPriorSelection_NewSelection** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - nothing selected
      - click: (4, 6) (white pawn)
    - **Expected output**: selected = (4, 6); highlight = {(4, 5), (4, 4)}; board unchanged; turn unchanged (WHITE)

- **C2: handleSquareClick_selectOwnPiece_NoValidDestinations_NoHighlights** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - nothing selected
      - click: (0, 7) (white rook, boxed in by the pawn on (0, 6) and the knight on (1, 7))
    - **Expected output**: selected = (0, 7); highlight = empty set (lower boundary of the highlight size interval); board unchanged; no exception

- **C3: handleSquareClick_selectEmptySquare_NoPriorSelection_NoChange** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - nothing selected
      - click: (4, 4) (empty)
    - **Expected output**: selected = `null`; highlight = empty set; board unchanged

- **C4: handleSquareClick_selectOpponentPiece_NoPriorSelection_NoChange** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - nothing selected
      - click: (4, 1) (black pawn)
    - **Expected output**: selected = `null` (black pieces cannot be selected on white's turn); highlight = empty set; board unchanged

#### selection populated

- **C5: handleSquareClick_moveToHighlightedSquare_moveCompletedAndTurnProgresses** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - select (4, 6) (highlight = {(4, 5), (4, 4)})
      - click: (4, 5) (inside the highlight)
    - **Expected output**: move performed; `snapshot[6][4]` is `null`, `snapshot[5][4]` is a WHITE PAWN; selected = `null`; highlight = empty set; turn becomes BLACK

- **C6: handleSquareClick_moveToHighlightedCaptureSquare_moveCompletedAndTurnProgresses** ( :x: )
    - **State of the system**:
      - white pawn (4, 6) -> (4, 4), black pawn (5, 1) -> (5, 3), WHITE to move
      - select (4, 4) (highlight = {(4, 3), (5, 3)})
      - click: (5, 3) (black pawn, inside the highlight)
    - **Expected output**: capture performed; `snapshot[3][5]` is a WHITE PAWN, the black pawn is gone, piece count is 31; selected = `null`; highlight = empty set; turn becomes BLACK

- **C7: handleSquareClick_clickSelectedSquareAgain_NoChange** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - select (4, 6)
      - click: (4, 6) (same square, not a destination)
    - **Expected output**: deselected; selected = `null`; highlight = empty set; board unchanged; turn unchanged (WHITE)

- **C8: handleSquareClick_clickOtherOwnPieceWhileSelected_NewSelection** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - select (4, 6)
      - click: (1, 7) (white knight)
    - **Expected output**: selection switches to (1, 7); highlight recomputed as {(0, 5), (2, 5)} (not the pawn's destinations); board unchanged; turn unchanged (WHITE)

- **C9: handleSquareClick_clickUnhighlightedEmptySquare_ClearSelection** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - select (4, 6)
      - click: (6, 5) (empty, outside the highlight)
    - **Expected output**: no move; board unchanged; selected = `null`; highlight = empty set; turn unchanged (WHITE)

- **C10: handleSquareClick_clickUnhighlightedOpponentPiece_ClearSelection** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - select (4, 6)
      - click: (3, 1) (black pawn, outside the highlight)
    - **Expected output**: no move; the black pawn is not captured; board unchanged; selected = `null`; highlight = empty set; turn unchanged (WHITE)

- **C11: handleSquareClick_clickOutsideBoardWhileSelected_NoChange** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - select (4, 6)
      - click: (8, 5) (outside the board)
    - **Expected output**: click ignored; selection is *kept* (selected = (4, 6), highlight = {(4, 5), (4, 4)}); board unchanged; turn unchanged (contrast with C12, where an on-board miss clears the selection)

### Method under test: `getSelectedSquare()`

- **C12: getSelectedSquare_NoClick_ReturnNull** ( :x: )
    - **State of the system**:
      - newly constructed `BoardController`, standard starting board
      - no click handled yet
    - **Expected output**: `null` 
  
- **C13: getSelectedSquare_PieceSelected_ReturnLocation** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - `handleSquareClick((4, 6))` (white pawn)
    - **Expected output**: `(4, 6)` 

- **C14: getSelectedSquare_AfterCompletedMove_ReturnNull** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - `handleSquareClick((4, 6))`, then `handleSquareClick((4, 5))` (move performed)
    - **Expected output**: `null` 
  
### Method under test: `getHighlightedSquares()`

- **C15: getHighlightedSquares_NoSelection_ReturnEmpty** ( :x: )
    - **State of the system**:
      - newly constructed `BoardController`
      - no click handled yet
    - **Expected output**: empty set 
  
- **C16: getHighlightedSquares_SelectionMade_returnEmpty** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - `handleSquareClick((0, 7))` (white rook, no legal destination)
    - **Expected output**: empty set, while `getSelectedSquare()` is `(0, 7)` — same size as C24 but a different reason, so "nothing selected" and "selected with no move" must not be conflated

- **C17: getHighlightedSquares_SelectionWithValidMoves_returnNonEmpty** ( :x: )
    - **State of the system**:
      - standard starting board, WHITE to move
      - `handleSquareClick((4, 6))` (white pawn)
    - **Expected output**: exactly `{(4, 5), (4, 4)}` — no extra squares, so the returned set is also the set the click case C1 accepts

### Method under test: `getCurrentTurn()`
work in progress

- **C18: getCurrentTurn_NewGame_ReturnWhite** ( :x: )
    - **State of the system**:
      - newly constructed `BoardController`
      - no click handled yet
    - **Expected output**: `PieceColor.WHITE` 

- **C19: getCurrentTurn_AfterOneMove_ReturnBlack** ( :x: )
    - **State of the system**:
        - after white moves once
    - **Expected output**: `PieceColor.BLACK` 