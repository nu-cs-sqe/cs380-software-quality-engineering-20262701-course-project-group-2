# BVA Analysis: `BoardController`

## Method under test: `handleSquareClick(Location location)`

**Purpose / use:** Converts a BoardView click into selection, reselection, deselection, or delegation of an approved move.

**Current behavior:**

1. off-board click → ignore and preserve current selection;
2. highlighted destination → call `Game.movePiece`, call `Game.switchTurn`, clear selection/highlights;
3. different active-player piece → select it and copy `Game.getLegalMoves(...)` into highlights;
4. every other in-board click → clear selection/highlights.

The controller unit tests below use a mocked Game. They prove controller decisions and calls, not real Board mutation.

### Nothing selected

| Test Case ID                                                                 | Method(s) under test          | State of the System                                                                | Expected Output                                                    | Implemented?       |
|------------------------------------------------------------------------------|-------------------------------|------------------------------------------------------------------------------------|--------------------------------------------------------------------|--------------------|
| C1<br><sub>handleSquareClick_SelectOwnPawn_NewSelection</sub>                | `handleSquareClick(Location)` | Mocked standard board; Game reports WHITE; click `(4,6)` and moves `{(4,5),(4,4)}` | selected `(4,6)`; highlights `{(4,5),(4,4)}`                       | :white_check_mark: |
| C2<br><sub>handleSquareClick_SelectOwnPieceWithNoMoves_EmptyHighlights</sub> | `handleSquareClick(Location)` | Mocked standard board; WHITE; click boxed-in rook `(0,7)`; Game returns `{}`       | selected `(0,7)`; highlights empty                                 | :white_check_mark: |
| C3<br><sub>handleSquareClick_ClickEmpty_NoSelection</sub>                    | `handleSquareClick(Location)` | Mocked standard board; WHITE; click empty `(4,4)`                                  | selected `null`; highlights empty                                  | :white_check_mark: |
| C4<br><sub>handleSquareClick_ClickOpponent_NoSelection</sub>                 | `handleSquareClick(Location)` | Mocked standard board; WHITE; click BLACK pawn `(4,1)`                             | selected `null`; highlights empty                                  | :white_check_mark: |
| C24<br><sub>handleSquareClick_GameReportsBlack_SelectBlackPiece</sub>        | `handleSquareClick(Location)` | Mock Game reports BLACK turn and black-pawn moves; click `(4,1)`                   | selected `(4,1)`; highlights equal Game result; turn remains BLACK | :white_check_mark: |

### Selection populated

| Test Case ID                                                                          | Method(s) under test          | State of the System                                                                       | Expected Output                                                                       | Implemented?       |
|---------------------------------------------------------------------------------------|-------------------------------|-------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|--------------------|
| C5<br><sub>handleSquareClick_HighlightedEmptyDestination_DelegateMoveAndClear</sub>   | `handleSquareClick(Location)` | Select `(4,6)`; click highlighted `(4,5)`                                                 | `Game.movePiece((4,6),(4,5))` and `switchTurn()` called; selection/highlights cleared | :white_check_mark: |
| C6<br><sub>handleSquareClick_HighlightedCaptureDestination_DelegateMoveAndClear</sub> | `handleSquareClick(Location)` | Mock board has WHITE `(4,4)`, BLACK `(5,3)`; select WHITE; click highlighted BLACK square | `Game.movePiece((4,4),(5,3))` and `switchTurn()` called; selection/highlights cleared | :white_check_mark: |
| C7<br><sub>handleSquareClick_ClickSelectedSquareAgain_ClearSelection</sub>            | `handleSquareClick(Location)` | Select `(4,6)`; click `(4,6)` again                                                       | selection/highlights cleared; no move call                                            | :white_check_mark: |
| C8<br><sub>handleSquareClick_ClickDifferentOwnPiece_Reselect</sub>                    | `handleSquareClick(Location)` | Select WHITE pawn; click WHITE knight `(1,7)`                                             | knight selected; highlights replaced with knight moves                                | :white_check_mark: |
| C9<br><sub>handleSquareClick_ClickUnhighlightedEmpty_ClearSelection</sub>             | `handleSquareClick(Location)` | Select WHITE pawn; click unhighlighted empty `(6,5)`                                      | selection/highlights cleared; no move call                                            | :white_check_mark: |
| C10<br><sub>handleSquareClick_ClickUnhighlightedOpponent_ClearSelection</sub>         | `handleSquareClick(Location)` | Select WHITE pawn; click unhighlighted BLACK piece `(3,1)`                                | selection/highlights cleared; no move call                                            | :white_check_mark: |
| C11<br><sub>handleSquareClick_ColumnEight_Ignore</sub>                                | `handleSquareClick(Location)` | Selection exists; click `(8,5)`                                                           | selection/highlights preserved                                                        | :white_check_mark: |
| C20<br><sub>handleSquareClick_ColumnNegativeOne_Ignore</sub>                          | `handleSquareClick(Location)` | Selection exists; click `(-1,5)`                                                          | selection/highlights preserved                                                        | :white_check_mark: |
| C21<br><sub>handleSquareClick_RowNegativeOne_Ignore</sub>                             | `handleSquareClick(Location)` | Selection exists; click `(5,-1)`                                                          | selection/highlights preserved                                                        | :white_check_mark: |
| C22<br><sub>handleSquareClick_RowEight_Ignore</sub>                                   | `handleSquareClick(Location)` | Selection exists; click `(5,8)`                                                           | selection/highlights preserved                                                        | :white_check_mark: |

---

## Getter methods

Simple getters do not need independent boundary analysis, but the existing observable-state tests are retained for traceability.

| Test Case ID                                                             | Method(s) under test      | State of the System                                    | Expected Output                            | Implemented?       |
|--------------------------------------------------------------------------|---------------------------|--------------------------------------------------------|--------------------------------------------|--------------------|
| C12<br><sub>getSelectedSquare_NoClick_ReturnNull</sub>                   | `getSelectedSquare()`     | New controller                                         | `null`                                     | :white_check_mark: |
| C13<br><sub>getSelectedSquare_AfterSelection_ReturnLocation</sub>        | `getSelectedSquare()`     | C1 selection performed                                 | `(4,6)`                                    | implemented by C1  |
| C14<br><sub>getSelectedSquare_AfterDelegatedMove_ReturnNull</sub>        | `getSelectedSquare()`     | C5 move delegation completed                           | `null`                                     | implemented by C5  |
| C15<br><sub>getHighlightedSquares_NoSelection_ReturnEmpty</sub>          | `getHighlightedSquares()` | New controller                                         | empty set                                  | :white_check_mark: |
| C16<br><sub>getHighlightedSquares_SelectedPieceNoMoves_ReturnEmpty</sub> | `getHighlightedSquares()` | C2 selection performed                                 | empty set while selected square is `(0,7)` | implemented by C2  |
| C17<br><sub>getHighlightedSquares_SelectedPawn_ReturnTwo</sub>           | `getHighlightedSquares()` | C1 selection performed                                 | `{(4,5),(4,4)}`                            | implemented by C1  |
| C23<br><sub>getHighlightedSquares_GameReturnsOneMove_ReturnOne</sub>     | `getHighlightedSquares()` | Mock Game returns exactly `{(4,6)}` for selected king  | exactly `{(4,6)}`                          | :white_check_mark: |
| C18<br><sub>getCurrentTurn_NewGame_ReturnWhite</sub>                     | `getCurrentTurn()`        | Controller with real new Game                          | WHITE                                      | :white_check_mark: |
| C19<br><sub>getCurrentTurn_AfterRealMove_ReturnBlack</sub>               | `getCurrentTurn()`        | Complete a real WHITE move through the controller      | BLACK                                      | implemented by C27 |
| C25<br><sub>getBoardSnapshot_NewController_Return8x8Snapshot</sub>       | `getBoardSnapshot()`      | Controller with real new Game                          | 8 rows and 8 columns per row               | :white_check_mark: |
| C26<br><sub>getBoardSnapshot_AfterRealMove_ReturnUpdatedBoard</sub>      | `getBoardSnapshot()`      | Complete real WHITE `(4,6) → (4,5)` through controller | source empty; destination WHITE PAWN       | implemented by C27 |

---

## Feature-thread integration cases

These cases use a real Game and Board. They are intentionally separate from C5/C6, which are controller unit tests with mocks.

| Test Case ID                                                                         | Methods under test                                                           | State of the System                                                            | Expected Output                                                                            | Implemented?       |
|--------------------------------------------------------------------------------------|------------------------------------------------------------------------------|--------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|--------------------|
| C27<br><sub>handleSquareClick_RealGameOrdinaryMove_UpdateBoardTurnAndSelection</sub> | `handleSquareClick`, `getBoardSnapshot`, `getCurrentTurn`, selection getters | New game; click `(4,6)`, then `(4,5)`                                          | WHITE pawn relocated; turn BLACK; selection/highlights empty                               | :white_check_mark: |
| C28<br><sub>handleSquareClick_RealGameCapture_UpdateBoardTurnAndPieceCount</sub>     | same feature thread                                                          | Arrange WHITE pawn `(4,4)`, BLACK pawn `(5,3)`, WHITE to move; capture `(5,3)` | WHITE pawn at destination; source empty; 31 pieces; turn BLACK; selection/highlights empty | :X:                |

## Removed stale API plans

`getGameMode()` and `getWinner()` are not current BoardController methods. They are deferred in [`../../design/future_design_scope.md`](../../design/future_design_scope.md).
