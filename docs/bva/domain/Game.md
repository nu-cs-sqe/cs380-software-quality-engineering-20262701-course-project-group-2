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

**Purpose / use:** Provides the set of destinations the selected piece is actually allowed to move to depending on board position relative to other pieces (blocked piece logic, capture logic)

**Expected behavior:** Starts from the piece's movement-level moves and filters/adds moves as required

Inputs: Set<Location> locations from getValidMoves, the current board, location the piece is moving from, the piece in question

Outputs: Set<Location>

| Test Case ID                                                                         | Method(s) under test                      | State of the System (format: board description (piece being input if necessary for clarity))                                               | Expected Output                                                                                                                                                                | Implemented? |
|--------------------------------------------------------------------------------------|-------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| M3<br><sub>getLegalMoves_Pawn_BlockedAhead_returnEmpty</sub>                         | `getLegalMoves(Location to, Piece piece)` | white pawn on (4,4), black pawn on (4,3) (white pawn)                                                                                      | `{}`                                                                                                                                                                           | :white_check_mark:          |
| M4<br><sub>getLegalMoves_Pawn_OneCapturePossible_returnTwo</sub>                     | `getLegalMoves(Location to, Piece piece)` | after white (4, 6) -> (4, 4) and black (5, 1) -> (5, 3) from: (4, 4) (white pawn)                                                          | `{(4, 3), (5, 3)}`                                                                                                                                                             | :white_check_mark:          |
| M5<br><sub>getLegalMoves_Pawn_TwoCapturesPossible_returnThree</sub>                  | `getLegalMoves(Location to, Piece piece)` | after white (4, 6) -> (4, 4), black (3, 1) -> (3, 3) and black (5, 1) -> (5, 3) (white pawn)                                               | `{(4, 3), (3, 3), (5, 3)}`                                                                                                                                                     | :white_check_mark:          |
| M40<br><sub>getLegalMoves_Pawn_OnStartRowBlockedAhead_returnEmpty</sub>              | `getLegalMoves(Location to, Piece piece)` | white pawn on (4, 6), an occupied square on (4, 5), (4, 4) empty                                                                           | `{}` — the pawn cannot move one square forward and cannot jump over the blocker to make the two-square opening move                                                            | :X:          |
| M41<br><sub>getLegalMoves_Pawn_OwnPieceDiagonal_NotCapture</sub>                     | `getLegalMoves(Location to, Piece piece)` | white pawn on (4, 4), (4, 3) empty, own piece on (5, 3)                                                                                    | `{(4, 3)}` — the own piece on the diagonal is not a valid capture destination                                                                                                  | :white_check_mark:          |
| M42<br><sub>getLegalMoves_Pawn_OpponentDirectlyAhead_NotCapture</sub>                | `getLegalMoves(Location to, Piece piece)` | white pawn on (4, 4), opponent piece on (4, 3), diagonal squares empty                                                                     | `{}` — a pawn cannot capture a piece directly in front of it                                                                                                                   | :white_check_mark:          |
| M43<br><sub>getLegalMoves_Pawn_OnLeftEdge_NoOffBoardDiagonal</sub>                   | `getLegalMoves(Location to, Piece piece)` | white pawn on (0, 4), opponent piece on (1, 3)                                                                                             | `{(0, 3), (1, 3)}` — no destination with column -1 is returned                                                                                                                 | :white_check_mark:          |
| M13<br><sub>getLegalMoves_Knight_PathBlocked_returnTwo</sub>                         | `getLegalMoves(Location to, Piece piece)` | standard starting board, knight surrounded by its own pawn and rook, from: (1, 7) (white knight)                                           | `{(0, 5), (2, 5)}` exactly — (3, 6) holds an own pawn and is excluded, and the two included squares are reached even though pieces are between the knight and the destinations | :white_check_mark:          |
| M19<br><sub>getLegalMoves_Knight_NoValidDestinations_returnEmpty</sub>               | `getLegalMoves(Location to, Piece piece)` | a white knight on (0, 0), a white rook on (1, 2) and a white bishop on (2, 1), every other square empty (white knight)                     | `{}`                                                                                                                                                                           | :white_check_mark:          |
| M20<br><sub>getLegalMoves_Knight_OpponentOnDestination_returnIncludingOpponent</sub> | `getLegalMoves(Location to, Piece piece)` | white knight on (3,4), black pawn on (0, 5), (2, 5) and (3, 6) are empty (white knight))                                                   | `{(0, 5), (2, 5), (3, 6)}`                                                                                                                                                     | :white_check_mark:          |
| M10<br><sub>getLegalMoves_Bishop_Blocked_returnEmpty</sub>                           | `getLegalMoves(Location to, Piece piece)` | white bishop on (2,7), own pawns on (1, 6) and (3, 6))                                                                                     | `{}`                                                                                                                                                                           | :white_check_mark:          |
| M12<br><sub>getLegalMoves_Bishop_OpponentInDiagonal_returnUpToOpponent</sub>         | `getLegalMoves(Location to, Piece piece)` | white pawn (3, 4), black pawn (4, 5), white bishop (2,7), (white bishop; diagonal should run into the black pawn on (4, 5))                | `{(3, 6), (4, 5)}` — the empty square before the opponent is included, the opponent's square is included as a capture, and nothing behind it is included                       | :white_check_mark:          |
| M6<br><sub>getLegalMoves_Rook_returnEmpty</sub>                                      | `getLegalMoves(Location to, Piece piece)` | white rook on (0,7), own pawn on (0, 6) and own knight on (1, 7)                                                                           | `{}`                                                                                                                                                                           | :white_check_mark:          |
| M7<br><sub>getLegalMoves_Rook_AdjacentTilesUnblocked_returnAdjacent</sub>            | `getLegalMoves(Location to, Piece piece)` | after white (7, 6) -> (7, 4) and white knight (6, 7) -> (5, 5), white rook on (7, 7) (white rook)                                          | `{(7, 6), (7, 5), (6, 7)}`                                                                                                                                                     | :white_check_mark:          |
| M9<br><sub>getLegalMoves_Rook_BlockedByOpponent_returnUpToOpponent</sub>             | `getLegalMoves(Location to, Piece piece)` | white rook on (7, 7), black piece on (7, 3), (7, 6), (7, 5), and (7, 4) empty, own piece on (6, 7), so the horizontal direction is blocked | `{(7, 6), (7, 5), (7, 4), (7, 3)}` — the opponent's square is included as a capture and nothing beyond it is included                                                          | :white_check_mark:          |
| M16<br><sub>getLegalMoves_Queen_BoxedIn_returnEmpty</sub>                            | `getLegalMoves(Location to, Piece piece)` | standard starting board, (white queen on (3,7), own pieces on (3, 6), (2, 6), (4, 6), (2, 7) and (4, 7))                                   | `{}` — every in-board direction is blocked by a friendly piece                                                                                                                 | :white_check_mark:          |
| M17<br><sub>getLegalMoves_Queen_AdjacentsCleared_returnAdjacent</sub>                | `getLegalMoves(Location to, Piece piece)` | standard starting board, white pawn (3, 6) -> (3, 4), (white queen)                                                                        | `{(3, 6), (3, 5)}` exactly — the cleared file up to the own pawn on (3, 4); the diagonals are still blocked by own pawns                                                       | :white_check_mark:          |
| M23<br><sub>getLegalMoves_Queen_OpponentInFile_returnUpToOpponent</sub>              | `getLegalMoves(Location to, Piece piece)` | white queen at (3,7), black pawn on (3, 3), (2, 6) and (4, 6) still hold own pawns (white queen)                                           | `{(3, 6), (3, 5), (3, 4), (3, 3)}`                                                                                                                                             | :white_check_mark:          |
| M14<br><sub>getLegalMoves_King_BoxedIn_returnEmpty</sub>                             | `getLegalMoves(Location to, Piece piece)` | standard starting board (white king, all five in-board neighbours hold own pieces)                                                         | `{}` — size 0 on the standard board                                                                                                                                            | :white_check_mark:          |
| M15<br><sub>getLegalMoves_King_OneNeighbourFreed_returnOne</sub>                     | `getLegalMoves(Location to, Piece piece)` | standard starting board, after white (4, 6) -> (4, 4) (white king; (4, 6) now empty)                                                       | `{(4, 6)}`                                                                                                                                                                     | :white_check_mark:          |
| M21<br><sub>getLegalMoves_King_OpponentAdjacent_returnIncludingOpponent</sub>        | `getLegalMoves(Location to, Piece piece)` | standard starting board, surroudning own pieces all the same but (4,6) has black pawn (white king)                                         | `{(4, 6)}`                                                                                                                                                                     | :white_check_mark:          |

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

**Purpose / use:** Determines whether a player's king is attacked.

**Expected behavior:** Returns `true` when the requested color's king is in check and `false` otherwise.

Inputs: current state of the board, color of the current player trying to make a move, the piece checking the king

Outputs: boolean

| Test Case ID | Method(s) under test          | State of the System | Expected Output | Implemented? |
|--------------|-------------------------------|---------------------|-----------------|--------------|
|   IC1     | `isInCheck(PieceColor color)` |    black king (2,0), white bishop (5,3), color = black      |    True    | :X:          |
|   IC2     | `isInCheck(PieceColor color)` |    black king (2,0), white bishop (5,3), black pawn (3,1), color = black      |    False    | :X:          |

---


### Planned method under test: `getPlayableMoves(Set<Location> location, Piece piece)`

**Purpose / use:** Determines the final set of playable moves that a player can move a piece after checking for king safety.

**Expected behavior:** Returns a set of playable moves filtering out any moves that would put the player's own piece in check, unless those moves would lead to checkmate 

Inputs: current state of the board, piece being moved, set of legal moves the piece can make

Outputs: set<location>

| Test Case ID                                                    | Method(s) under test | State of the System                                                                | Expected Output | Implemented? |
|-----------------------------------------------------------------|----------------------|------------------------------------------------------------------------------------|-----------------|--------------|
| PM1<br/>getPlayableMoves_PawnBlockingCheck_returnEmpty          | `getPlayableMoves`   | black king (2,0), white bishop (5,3), black pawn (3,1) (black pawn on 3,1) | `{}` - can't move anywhere         | :X:          |
| PM2<br/>getPlayableMoves_BishopBlockingCheck_returnMultipleAllowableMoves | `getPlayableMoves`   | black king (2,0), white bishop (5,3), black bishop (3,1) (black bishop)        |  `{(4,2),(5,3),(6,4)}`       | :X:          |

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

