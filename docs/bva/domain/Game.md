# BVA Analysis for Game

### Planned constructor under test: `Game(GameMode gameMode)`

**Purpose / use:** Creates a new chess game using the game mode selected by the players.

**Expected behavior:** Creates a standard new game, stores the selected mode, and begins with WHITE to move.

Inputs:

Outputs:

| Test Case ID | Method(s) under test      | State of the System | Expected Output | Implemented? |
|--------------|---------------------------|---------------------|-----------------|--------------|
|              | `Game(GameMode gameMode)` |                     |                 | :X:          |

---

### Method under test: `getCurrentTurn()`

**Purpose / use:** Lets the controller and game-stat view know which color may currently select/move pieces.

**Expected behavior:** Returns the color whose turn it currently is.

Inputs:
- State Case: newly initialized game
- Current Turn Case: WHITE, BLACK
    - For initial game setup, only WHITE should be set.
    - BLACK becomes reachable once `switchTurn()` is called — moving a piece does not switch the turn by itself.

Outputs:
- `PieceColor` Case: WHITE, BLACK

| Test Case ID | Method(s) under test | State of the System      | Expected Output    | Implemented?       |
|--------------|----------------------|--------------------------|--------------------|--------------------|
| G1           | `getCurrentTurn()`   | Newly initialized `Game` | `PieceColor.WHITE` | :white_check_mark: |

### Method under test: `getBoardSnapshot()`

**Purpose / use:** Gives the controller/view access to the current board through Game so those classes do not need to own or manipulate Board directly.

**Expected behavior:** Returns the current Board snapshot.

| Test Case ID                                                     | Method(s) under test | State of the System                                       | Expected Output                                                                   | Implemented? |
|------------------------------------------------------------------|----------------------|-----------------------------------------------------------|-----------------------------------------------------------------------------------|--------------|
| G5<br><sub>getBoardSnapshot_NewGame_ReturnInitializedBoard</sub> | `getBoardSnapshot()` | newly initialized `Game`                                  | snapshot satisfies the initialized-board behavior documented by Board tests B1-B4 | :X:          |
| G6<br><sub>getBoardSnapshot_AfterMove_ReturnUpdatedBoard</sub>   | `getBoardSnapshot()` | newly initialized `Game`<br>• `movePiece((4, 6), (4, 5))` | source square is empty and destination contains the WHITE PAWN                    | :X:          |

---

### Planned method under test: `getGameMode()`

**Purpose / use:** Lets controller/view code know whether the game is Classic or King of the Hill.

**Expected behavior:** Returns the `GameMode` selected when the game was created.

Inputs:

Outputs:

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `getGameMode()`      |                     |                 | :X:          |

---

### Planned method under test: `getLegalMoves(Location location)`

**Purpose / use:** Provides the final set of destinations the selected piece is actually allowed to move to after Game-level rules are applied.

**Expected behavior:** Starts from the piece's movement-level moves and filters/adds moves as required by rules such as king safety and castling.

Inputs:

Outputs:

| Test Case ID | Method(s) under test               | State of the System | Expected Output | Implemented? |
|--------------|------------------------------------|---------------------|-----------------|--------------|
|              | `getLegalMoves(Location location)` |                     |                 | :X:          |

#### Moved tests from the piece `getValidMoves()` BVAs

The cases below were originally documented as `getValidMoves()` tests in the concrete piece BVA files. They were moved here because each one depends on board state other than the selected piece's own location — a blocker in the path, an own piece on a destination, or an opponent piece on a capture square.

`getValidMoves()` now only returns a piece's movement pattern from its location and does not read any other square, so board-dependent cases belong to `getLegalMoves()`, which is the source of truth for whether a move is valid. They are kept with their original IDs, names, states and expected outputs (nothing is renumbered), which is why the piece files now have gaps in their numbering. Re-expressing these cases with full Game-level state (both kings, check state, castling rights) is still to be done.

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
| M3<br><sub>getValidMoves_Pawn_BlockedAhead_returnEmpty</sub>            | `getLegalMoves(Location location)`    | after white (4, 6) -> (4, 4) and black (4, 1) -> (4, 3)<br>• from: (4, 4) (white pawn)                         | `{}`                                                                                                                                               | :X:          |
| M4<br><sub>getValidMoves_Pawn_OneCapturePossible_returnTwo</sub>        | `getLegalMoves(Location location)`    | after white (4, 6) -> (4, 4) and black (5, 1) -> (5, 3)<br>• from: (4, 4) (white pawn)                         | `{(4, 3), (5, 3)}`                                                                                                                                 | :X:          |
| M5<br><sub>getValidMoves_Pawn_TwoCapturesPossible_returnThree</sub>     | `getLegalMoves(Location location)`    | after white (4, 6) -> (4, 4), black (3, 1) -> (3, 3) and black (5, 1) -> (5, 3)<br>• from: (4, 4) (white pawn) | `{(4, 3), (3, 3), (5, 3)}`                                                                                                                         | :X:          |
| M40<br><sub>getValidMoves_Pawn_OnStartRowBlockedAhead_returnEmpty</sub> | `getLegalMoves(Location location)`    | white pawn on (4, 6)<br>• an occupied square on (4, 5)<br>• (4, 4) empty                                       | `{}` — the pawn cannot move one square forward and cannot jump over the blocker to make the two-square opening move                                | :X:          |
| M41<br><sub>getValidMoves_Pawn_OwnPieceDiagonal_NotCapture</sub>        | `getLegalMoves(Location location)`    | white pawn on (4, 4)<br>• (4, 3) empty<br>• own piece on (5, 3)                                                | `{(4, 3)}` — the own piece on the diagonal is not a valid capture destination                                                                      | :X:          |
| M42<br><sub>getValidMoves_Pawn_OpponentDirectlyAhead_NotCapture</sub>   | `getLegalMoves(Location location)`    | white pawn on (4, 4)<br>• opponent piece on (4, 3)<br>• diagonal squares empty                                 | `{}` — a pawn cannot capture a piece directly in front of it                                                                                       | :X:          |
| M43<br><sub>getValidMoves_Pawn_OnLeftEdge_NoOffBoardDiagonal</sub>      | `getLegalMoves(Location location)`    | white pawn on (0, 4)<br>• (0, 3) empty<br>• opponent piece on (1, 3)                                           | `{(0, 3), (1, 3)}` — no destination with column -1 is returned                                                                                     | :X:          |
| M13<br><sub>getValidMoves_Knight_PathBlocked_returnTwo</sub>                         | `getLegalMoves(Location location)`    | standard starting board, knight surrounded by its own pawn and rook<br>• from: (1, 7) (white knight)                                                                                          | `{(0, 5), (2, 5)}` exactly — (3, 6) holds an own pawn and is excluded, and the two included squares are reached even though pieces are between the knight and the destinations | :X:          |
| M19<br><sub>getValidMoves_Knight_NoValidDestinations_returnEmpty</sub>               | `getLegalMoves(Location location)`    | a white knight on (0, 0), a white rook on (1, 2) and a white bishop on (2, 1), every other square empty<br>• from: (0, 0) white knight in the corner                                          | `{}`                                                                                                                                                                           | :X:          |
| M20<br><sub>getValidMoves_Knight_OpponentOnDestination_returnIncludingOpponent</sub> | `getLegalMoves(Location location)`    | after white (3, 6) -> (3, 4), black (0, 1) -> (0, 3), black (0, 3) -> (0, 4), black (0, 4) -> (0, 5)<br>• from: (1, 7) (white knight; (0, 5) holds a black pawn, (2, 5) and (3, 6) are empty) | `{(0, 5), (2, 5), (3, 6)}`                                                                                                                                                     | :X:          |
| M10<br><sub>getValidMoves_Bishop_Blocked_returnEmpty</sub>                    | `getLegalMoves(Location location)`    | standard starting board<br>• from: (2, 7) (white bishop, own pawns on (1, 6) and (3, 6))                                                                                                                            | `{}`                                                                                                                                                     | :X:          |
| M11<br><sub>getValidMoves_Bishop_OpenDiagonal_returnDiagonal</sub>            | `getLegalMoves(Location location)`    | after white (3, 6) -> (3, 4)<br>• from: (2, 7) (white bishop)                                                                                                                                                       | `{(3, 6), (4, 5), (5, 4), (6, 3), (7, 2)}`                                                                                                               | :X:          |
| M12<br><sub>getValidMoves_Bishop_OpponentInDiagonal_returnUpToOpponent</sub>  | `getLegalMoves(Location location)`    | after white (3, 6) -> (3, 4), black (4, 1) -> (4, 3), black (4, 3) -> (4, 4), black (4, 4) -> (4, 5)<br>• from: (2, 7) (white bishop; its diagonal now runs through the empty (3, 6) into the black pawn on (4, 5)) | `{(3, 6), (4, 5)}` — the empty square before the opponent is included, the opponent's square is included as a capture, and nothing behind it is included | :X:          |
| M6<br><sub>getValidMoves_Rook_returnEmpty</sub>                             | `getLegalMoves(Location location)`    | standard starting board<br>• from: (0, 7) (white rook, own pawn on (0, 6) and own knight on (1, 7))                                                    | `{}`                                                                                                                  | :X:          |
| M7<br><sub>getValidMoves_Rook_AdjacentTilesUnblocked_returnAdjacent</sub>   | `getLegalMoves(Location location)`    | after white (7, 6) -> (7, 4) and white knight (6, 7) -> (5, 5)<br>• from: (7, 7) (white rook)                                                          | `{(7, 6), (7, 5), (6, 7)}`                                                                                            | :X:          |
| M9<br><sub>getValidMoves_Rook_BlockedByOpponent_returnUpToOpponent</sub>    | `getLegalMoves(Location location)`    | white rook on (7, 7)<br>• black piece on (7, 3)<br>• (7, 6), (7, 5), and (7, 4) empty<br>• own piece on (6, 7), so the horizontal direction is blocked | `{(7, 6), (7, 5), (7, 4), (7, 3)}` — the opponent's square is included as a capture and nothing beyond it is included | :X:          |
| M16<br><sub>getValidMoves_Queen_BoxedIn_returnEmpty</sub>                       | `getLegalMoves(Location location)`    | standard starting board<br>• from: (3, 7) (white queen, own pieces on (3, 6), (2, 6), (4, 6), (2, 7) and (4, 7))                                                                                                                                                            | `{}` — every in-board direction is blocked by a friendly piece                                                                                                                                                                                                                                      | :X:          |
| M17<br><sub>getValidMoves_Queen_AdjacentsCleared_returnAdjacent</sub>           | `getLegalMoves(Location location)`    | after white (3, 6) -> (3, 4)<br>• from: (3, 7) (white queen)                                                                                                                                                                                                                | `{(3, 6), (3, 5)}` exactly — the cleared file up to the own pawn on (3, 4); the diagonals are still blocked by own pawns                                                                                                                                                                            | :X:          |
| M23<br><sub>getValidMoves_Queen_OpponentInFile_returnUpToOpponent</sub>         | `getLegalMoves(Location location)`    | after black (4, 1) -> (4, 3), black (4, 3) -> (4, 4), black (4, 4) -> (4, 5), white (3, 6) captures (4, 5), black (3, 1) -> (3, 3)<br>• from: (3, 7) (white queen; the file below it is empty down to the black pawn on (3, 3), and (2, 6) and (4, 6) still hold own pawns) | `{(3, 6), (3, 5), (3, 4), (3, 3)}`                                                                                                                                                                                                                                                                  | :X:          |
| M14<br><sub>getValidMoves_King_BoxedIn_returnEmpty</sub>                      | `getLegalMoves(Location location)`    | standard starting board<br>• from: (4, 7) (white king, all five in-board neighbours hold own pieces)                                                                                                          | `{}` — size 0 on the standard board                                | :X:          |
| M15<br><sub>getValidMoves_King_OneNeighbourFreed_returnOne</sub>              | `getLegalMoves(Location location)`    | after white (4, 6) -> (4, 4)<br>• from: (4, 7) (white king; (4, 6) now empty)                                                                                                                                 | `{(4, 6)}`                                                         | :X:          |
| M21<br><sub>getValidMoves_King_OpponentAdjacent_returnIncludingOpponent</sub> | `getLegalMoves(Location location)`    | after black (5, 1) -> (5, 3), black (5, 3) -> (5, 4), black (5, 4) -> (5, 5), black (5, 5) captures white (4, 6)<br>• from: (4, 7) (white king; (4, 6) now holds a black pawn, surrounding own pieces remain) | `{(4, 6)}`                                                         | :X:          |

---

### Method under test: `movePiece(Location from, Location to)`

**Purpose / use:** Executes an already-approved move. The controller calls this after the destination has been confirmed.

**Expected behavior:** Delegates the board relocation to `Board.movePiece()`. `movePiece()` itself does not switch the turn; `switchTurn()` remains separate.

| Test Case ID                             | Method(s) under test                    | State of the System                                               | Expected Output                                                                                                                                                                                          | Implemented? |
|------------------------------------------|-----------------------------------------|-------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| G2<br><sub>movePiece_Game_CallMade</sub> | `movePiece(Location from, Location to)` | newly initialized game<br>• move white pawn from (4, 6) -> (4, 4) | Board state reflects the move; if `Board` is mocked for this unit test, `Board.movePiece((4, 6), (4, 4))` is called; current turn remains WHITE because `movePiece()` does not switch the turn by itself | :X:          |


### Method under test: `switchTurn()`

**Purpose / use:** Advances the game to the other player after a completed move. Keeping it separate makes turn changes explicit and easy to test.

**Expected behavior:** Changes the current turn from WHITE to BLACK or BLACK to WHITE. It does not alter the board.

| Test Case ID                                  | Method(s) under test | State of the System                                                | Expected Output                          | Implemented? |
|-----------------------------------------------|----------------------|--------------------------------------------------------------------|------------------------------------------|--------------|
| G3<br><sub>switchTurn_White_ReturnBlack</sub> | `switchTurn()`       | newly initialized game; current turn is WHITE                      | `getCurrentTurn()` is `PieceColor.BLACK` | :X:          |
| G4<br><sub>switchTurn_Black_ReturnWhite</sub> | `switchTurn()`       | `switchTurn()` has already been called once; current turn is BLACK | `getCurrentTurn()` is `PieceColor.WHITE` | :X:          |

---

### Planned method under test: `isInCheck(PieceColor color)`

**Purpose / use:** Determines whether a player's king is currently attacked.

**Expected behavior:** Returns `true` when the requested color's king is in check and `false` otherwise.

Inputs:

Outputs:

| Test Case ID | Method(s) under test          | State of the System | Expected Output | Implemented? |
|--------------|-------------------------------|---------------------|-----------------|--------------|
|              | `isInCheck(PieceColor color)` |                     |                 | :X:          |

---

### Planned method under test: `isCheckmate(PieceColor color)`

**Purpose / use:** Determines whether a player has lost by checkmate.

**Expected behavior:** Returns `true` when the requested color is in check and has no legal move that escapes it; otherwise returns `false`.

Inputs:

Outputs:

| Test Case ID | Method(s) under test            | State of the System | Expected Output | Implemented? |
|--------------|---------------------------------|---------------------|-----------------|--------------|
|              | `isCheckmate(PieceColor color)` |                     |                 | :X:          |

---

### Planned method under test: `getWinner()`

**Purpose / use:** Provides one result query for the controller/view after a game-ending condition occurs.

**Expected behavior:** Returns the winning `PieceColor`, or `null` while the game has no winner.

Inputs:

Outputs:

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `getWinner()`        |                     |                 | :X:          |

