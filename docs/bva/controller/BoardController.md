# BVA Analysis: `BoardController`

### Method under test: `handleSquareClick(Location location)`

**Purpose / use:** This is the controller entry point called by `BoardView` whenever the user clicks a square. It converts raw UI clicks into selection, re-selection, or a legal move.

**Expected behavior:** Converts a board click into selection/re-selection/move behavior. It should only allow the active player's pieces to be selected, use `Game.getLegalMoves(...)` for highlights, perform a move only when the clicked destination is highlighted, clear selection after a completed/invalid move attempt as specified below, and advance the turn after a completed move.

1. Nothing selected + click a piece of the active player's colour -> that square becomes selected and its valid destinations are highlighted
2. Piece selected + click a highlighted destination -> the move is performed, and the selection and highlight are cleared
3. Piece selected + click another own piece -> selection changes to the newly clicked own piece and highlights are recomputed
4. Piece selected + click anything else on the board -> no move and the selection is cleared

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
- Board snapshot: cases {unchanged, one piece relocated, one piece captured}
- Current turn: cases {unchanged, switched}

### Step 4: Test Cases

#### Nothing selected

| Test Case ID                                                                       | Method(s) under test                   | State of the System                                                                                                                                   | Expected Output                                                                                             | Implemented? |
|------------------------------------------------------------------------------------|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|--------------|
| C1<br><sub>handleSquareClick_selectOwnPawn_NoPriorSelection_NewSelection</sub>     | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• nothing selected<br>• click: (4, 6) (white pawn)                                                          | selected = (4, 6); highlight = {(4, 5), (4, 4)}; board unchanged; turn unchanged (WHITE)                    | :X:          |
| C2<br><sub>handleSquareClick_selectOwnPiece_NoValidDestinations_NoHighlights</sub> | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• nothing selected<br>• click: (0, 7) (white rook, boxed in by the pawn on (0, 6) and the knight on (1, 7)) | selected = (0, 7); highlight = empty set; board unchanged; no exception                                     | :X:          |
| C3<br><sub>handleSquareClick_selectEmptySquare_NoPriorSelection_NoChange</sub>     | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• nothing selected<br>• click: (4, 4) (empty)                                                               | selected = `null`; highlight = empty set; board unchanged                                                   | :X:          |
| C4<br><sub>handleSquareClick_selectOpponentPiece_NoPriorSelection_NoChange</sub>   | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• nothing selected<br>• click: (4, 1) (black pawn)                                                          | selected = `null` (black pieces cannot be selected on white's turn); highlight = empty set; board unchanged | :X:          |

#### Selection populated

| Test Case ID                                                                                     | Method(s) under test                   | State of the System                                                                                                                                                             | Expected Output                                                                                                                                              | Implemented? |
|--------------------------------------------------------------------------------------------------|----------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| C5<br><sub>handleSquareClick_moveToHighlightedSquare_moveCompletedAndTurnProgresses</sub>        | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• select (4, 6) (highlight = {(4, 5), (4, 4)})<br>• click: (4, 5) (inside the highlight)                                              | move performed; `snapshot[6][4]` is `null`, `snapshot[5][4]` is a WHITE PAWN; selected = `null`; highlight = empty set; turn becomes BLACK                   | :X:          |
| C6<br><sub>handleSquareClick_moveToHighlightedCaptureSquare_moveCompletedAndTurnProgresses</sub> | `handleSquareClick(Location location)` | white pawn (4, 6) -> (4, 4), black pawn (5, 1) -> (5, 3), WHITE to move<br>• select (4, 4) (highlight = {(4, 3), (5, 3)})<br>• click: (5, 3) (black pawn, inside the highlight) | capture performed; `snapshot[3][5]` is a WHITE PAWN, the black pawn is gone, piece count is 31; selected = `null`; highlight = empty set; turn becomes BLACK | :X:          |
| C7<br><sub>handleSquareClick_clickSelectedSquareAgain_NoChange</sub>                             | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• select (4, 6)<br>• click: (4, 6) (same square, not a destination)                                                                   | deselected; selected = `null`; highlight = empty set; board unchanged; turn unchanged (WHITE)                                                                | :X:          |
| C8<br><sub>handleSquareClick_clickOtherOwnPieceWhileSelected_NewSelection</sub>                  | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• select (4, 6)<br>• click: (1, 7) (white knight)                                                                                     | selection switches to (1, 7); highlight recomputed as {(0, 5), (2, 5)}; board unchanged; turn unchanged (WHITE)                                              | :X:          |
| C9<br><sub>handleSquareClick_clickUnhighlightedEmptySquare_ClearSelection</sub>                  | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• select (4, 6)<br>• click: (6, 5) (empty, outside the highlight)                                                                     | no move; board unchanged; selected = `null`; highlight = empty set; turn unchanged (WHITE)                                                                   | :X:          |
| C10<br><sub>handleSquareClick_clickUnhighlightedOpponentPiece_ClearSelection</sub>               | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• select (4, 6)<br>• click: (3, 1) (black pawn, outside the highlight)                                                                | no move; the black pawn is not captured; board unchanged; selected = `null`; highlight = empty set; turn unchanged (WHITE)                                   | :X:          |
| C11<br><sub>handleSquareClick_clickOutsideBoardUpperColumnWhileSelected_NoChange</sub>           | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• select (4, 6)<br>• click: (8, 5) (column one above the valid interval)                                                              | click ignored; selection is kept (selected = (4, 6), highlight = {(4, 5), (4, 4)}); board unchanged; turn unchanged                                          | :X:          |
| C20<br><sub>handleSquareClick_clickOutsideBoardLowerColumnWhileSelected_NoChange</sub>           | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• select (4, 6)<br>• click: (-1, 5) (column one below the valid interval)                                                             | click ignored; selection and highlights are kept; board unchanged; turn unchanged                                                                            | :X:          |
| C21<br><sub>handleSquareClick_clickOutsideBoardLowerRowWhileSelected_NoChange</sub>              | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• select (4, 6)<br>• click: (5, -1) (row one below the valid interval)                                                                | click ignored; selection and highlights are kept; board unchanged; turn unchanged                                                                            | :X:          |
| C22<br><sub>handleSquareClick_clickOutsideBoardUpperRowWhileSelected_NoChange</sub>              | `handleSquareClick(Location location)` | standard starting board, WHITE to move<br>• select (4, 6)<br>• click: (5, 8) (row one above the valid interval)                                                                 | click ignored; selection and highlights are kept; board unchanged; turn unchanged                                                                            | :X:          |
| C24<br><sub>handleSquareClick_BlackTurn_SelectBlackPiece</sub>                                   | `handleSquareClick(Location location)` | one valid WHITE move has been completed, so current turn is BLACK<br>• nothing selected<br>• click: (4, 1) (black pawn)                                                         | selected = (4, 1); highlights are the black pawn's valid destinations; board unchanged; turn remains BLACK                                                   | :X:          |

### Method under test: `getSelectedSquare()`

**Purpose / use:** Lets the view know which square, if any, should be shown as selected.

**Expected behavior:** Returns the currently selected board square, or `null` when no piece is selected.

| Test Case ID                                                      | Method(s) under test  | State of the System                                                                                                        | Expected Output | Implemented? |
|-------------------------------------------------------------------|-----------------------|----------------------------------------------------------------------------------------------------------------------------|-----------------|--------------|
| C12<br><sub>getSelectedSquare_NoClick_ReturnNull</sub>            | `getSelectedSquare()` | newly constructed `BoardController`, standard starting board<br>• no click handled yet                                     | `null`          | :X:          |
| C13<br><sub>getSelectedSquare_PieceSelected_ReturnLocation</sub>  | `getSelectedSquare()` | standard starting board, WHITE to move<br>• `handleSquareClick((4, 6))` (white pawn)                                       | `(4, 6)`        | :X:          |
| C14<br><sub>getSelectedSquare_AfterCompletedMove_ReturnNull</sub> | `getSelectedSquare()` | standard starting board, WHITE to move<br>• `handleSquareClick((4, 6))`, then `handleSquareClick((4, 5))` (move performed) | `null`          | :X:          |

### Method under test: `getHighlightedSquares()`

**Purpose / use:** Supplies the legal destination squares that `BoardView` should highlight for the player.

**Expected behavior:** Returns the current set of fully legal destination squares for the selected piece. The set is empty when nothing is selected or when the selected piece has no legal move.

| Test Case ID                                                                   | Method(s) under test      | State of the System                                                                                        | Expected Output                                                                                                                                                       | Implemented? |
|--------------------------------------------------------------------------------|---------------------------|------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| C15<br><sub>getHighlightedSquares_NoSelection_ReturnEmpty</sub>                | `getHighlightedSquares()` | newly constructed `BoardController`<br>• no click handled yet                                              | empty set                                                                                                                                                             | :X:          |
| C16<br><sub>getHighlightedSquares_SelectionMade_returnEmpty</sub>              | `getHighlightedSquares()` | standard starting board, WHITE to move<br>• `handleSquareClick((0, 7))` (white rook, no valid destination) | empty set, while `getSelectedSquare()` is `(0, 7)` — same size as C15 but a different reason, so "nothing selected" and "selected with no move" must not be conflated | :X:          |
| C17<br><sub>getHighlightedSquares_SelectionWithValidMoves_returnNonEmpty</sub> | `getHighlightedSquares()` | standard starting board, WHITE to move<br>• `handleSquareClick((4, 6))` (white pawn)                       | exactly `{(4, 5), (4, 4)}`                                                                                                                                            | :X:          |
| C23<br><sub>getHighlightedSquares_SelectionWithExactlyOneMove_returnOne</sub>  | `getHighlightedSquares()` | use the board state from King test M15<br>• WHITE to move<br>• select the white king on (4, 7)             | exactly `{(4, 6)}` — covers the exactly-one boundary for the highlighted set size                                                                                     | :X:          |

### Method under test: `getCurrentTurn()`

**Purpose / use:** Gives the game-stat UI the same current-turn information owned by Game.

**Expected behavior:** Exposes the current turn from `Game` for the views/controller UI state.

| Test Case ID                                              | Method(s) under test | State of the System                                                       | Expected Output    | Implemented? |
|-----------------------------------------------------------|----------------------|---------------------------------------------------------------------------|--------------------|--------------|
| C18<br><sub>getCurrentTurn_NewGame_ReturnWhite</sub>      | `getCurrentTurn()`   | newly constructed `BoardController`<br>• no click handled yet             | `PieceColor.WHITE` | :X:          |
| C19<br><sub>getCurrentTurn_AfterOneMove_ReturnBlack</sub> | `getCurrentTurn()`   | after one valid WHITE move has been completed through `handleSquareClick` | `PieceColor.BLACK` | :X:          |

### Method under test: `getBoardSnapshot()`

**Purpose / use:** Gives `BoardView` the current board it needs to draw.

**Expected behavior:** Exposes the current board snapshot from `Game` for display.

| Test Case ID                                                            | Method(s) under test | State of the System                                                                       | Expected Output                                                | Implemented? |
|-------------------------------------------------------------------------|----------------------|-------------------------------------------------------------------------------------------|----------------------------------------------------------------|--------------|
| C25<br><sub>getBoardSnapshot_NewController_ReturnInitializedBoard</sub> | `getBoardSnapshot()` | newly constructed `BoardController`                                                       | initialized board snapshot satisfying Board setup tests B1-B4  | :X:          |
| C26<br><sub>getBoardSnapshot_AfterMove_ReturnUpdatedBoard</sub>         | `getBoardSnapshot()` | standard starting board<br>• move white pawn (4, 6) -> (4, 5) through `handleSquareClick` | source square is empty and destination contains the WHITE PAWN | :X:          |

---

### Planned method under test: `getGameMode()`

**Purpose / use:** Lets the UI read the selected ruleset through the controller instead of accessing Game directly.

**Expected behavior:** Returns the current Game's `GameMode`.

Inputs:

Outputs:

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `getGameMode()`      |                     |                 | :X:          |

---

### Planned method under test: `getWinner()`

**Purpose / use:** Lets the UI determine whether there is a winner and which player won.

**Expected behavior:** Returns the current Game's winner, or `null` while no winner exists.

Inputs:

Outputs:

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `getWinner()`        |                     |                 | :X:          |
