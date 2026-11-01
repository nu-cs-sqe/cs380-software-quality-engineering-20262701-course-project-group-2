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

