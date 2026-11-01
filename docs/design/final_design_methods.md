# Planned Methods for the Final Design

## `Main`

- `main(String[] args)` - **Purpose:** starts the application. **Used by:** Java when the program is launched; it creates the initial UI.

## `domain.Location`

- `getColumn()` - **Purpose:** returns the stored column coordinate. **Used by:** Board/Game logic whenever a location needs to be indexed on the board.
- `getRow()` - **Purpose:** returns the stored row coordinate. **Used by:** Board/Game logic whenever a location needs to be indexed on the board.
- `equals(Object other)` - **Purpose:** makes two locations with the same row/column count as the same location. **Used by:** move/highlight collections and tests such as `contains(new Location(...))`.
- `hashCode()` - **Purpose:** keeps hash-based collections consistent with `equals()`. **Used by:** `Set<Location>` / `HashSet<Location>` so equal coordinates are not treated as different elements.

## `domain.Board`

- `getSnapshot()` - **Purpose:** returns a copy of the current board. **Used by:** Game/controller/view code that needs to display or inspect board state without owning the internal array.
- `getPieceAt(Location location)` - **Purpose:** returns the piece on one square, or `null`. **Used by:** Game logic to inspect a selected square without scanning the whole snapshot.
- `getValidMovesByPiece(Location location)` - **Purpose:** asks the piece on a square for its movement-level destinations. **Used by:** `Game.getLegalMoves()` as the starting set before Game-level legality filters are applied.
- `movePiece(Location from, Location to)` - **Purpose:** performs the actual board relocation/capture. **Used by:** `Game.movePiece()` after a move has already been approved. If the approved move is castling, Board also relocates the rook.
- `findKing(PieceColor color)` - **Purpose:** finds a player's king on the current board. **Used by:** `Game.isInCheck()` and legal-move filtering.

## `domain.Game`

- `Game(GameMode gameMode)` - **Purpose:** creates a new game in the selected mode. **Used by:** `MainView` when the players start a game.
- `getCurrentTurn()` - **Purpose:** returns whose turn it is. **Used by:** controller/view logic to allow selection only for the active player and display the current player.
- `getBoardSnapshot()` - **Purpose:** exposes the Board snapshot through Game. **Used by:** the controller/view so they do not need direct Board ownership.
- `getGameMode()` - **Purpose:** returns `CLASSIC` or `KING_OF_THE_HILL`. **Used by:** rule logic and any UI that displays the chosen mode.
- `getLegalMoves(Location location)` - **Purpose:** returns every move the selected piece can legally make in the current game state. **Used by:** the controller to highlight destinations and decide whether a clicked destination is valid. It starts with Board's movement-level moves, filters self-check/king-into-check, and adds castling only when all castling rules are satisfied.
- `movePiece(Location from, Location to)` - **Purpose:** performs an already-approved move through Board and updates internal Game state such as castling rights and winner state. **Used by:** the controller after the player clicks a legal destination.
- `switchTurn()` - **Purpose:** changes WHITE to BLACK or BLACK to WHITE. **Used by:** the controller after a completed move when the game has not ended.
- `isInCheck(PieceColor color)` - **Purpose:** determines whether that player's king is currently attacked. **Used by:** legal-move filtering and `isCheckmate()`.
- `isCheckmate(PieceColor color)` - **Purpose:** determines whether that player is in check and has no legal response. **Used by:** win detection after a move.
- `getWinner()` - **Purpose:** provides one final-result query for both Classic checkmate and King of the Hill. **Used by:** controller/view logic; `null` means the game is still active, while WHITE or BLACK means the game has ended and identifies the winner.

## `domain.piece.Piece`

- `getType()` - **Purpose:** returns the piece type. **Used by:** Board/Game logic when behavior depends on PAWN, ROOK, KING, etc.
- `getColor()` - **Purpose:** returns WHITE or BLACK. **Used by:** turn ownership, captures, and opponent checks.
- `makeCopy()` - **Purpose:** creates a distinct copy with the same type/color. **Used by:** board snapshots or simulated board states without sharing the original piece object.
- `toString()` - **Purpose:** returns a readable piece description. **Used by:** debugging/display where a text form is useful.
- `getValidMoves(...)` - **Purpose:** returns the movement-level destinations allowed by that piece's own movement rules. **Used by:** Board as the first stage of move generation before Game applies king-safety/special-game rules.

## `Piece Classes`

`Pawn`, `Rook`, `Knight`, `Bishop`, `Queen`, and `King` each implement:

- `makeCopy()` - **Purpose:** returns a same-type copy with the same color. **Used by:** snapshots/simulated board states.
- `getValidMoves(...)` - **Purpose:** returns movement-level destinations for that piece. **Used by:** Board/Game move generation. `King.getValidMoves()` covers normal one-square king movement only; castling is added by `Game.getLegalMoves()`.

## `controller.BoardController`

- `handleSquareClick(Location location)` - **Purpose:** turns a board click into selection, re-selection, or a move. **Used by:** `BoardView` when the user clicks a square.
- `getSelectedSquare()` - **Purpose:** returns the currently selected square or `null`. **Used by:** the view to show selection state.
- `getHighlightedSquares()` - **Purpose:** returns the selected piece's legal destinations. **Used by:** the view to highlight squares the player may click.
- `getCurrentTurn()` - **Purpose:** exposes the Game's current turn. **Used by:** game-stat UI.
- `getBoardSnapshot()` - **Purpose:** exposes the current board. **Used by:** `BoardView` to draw pieces.
- `getGameMode()` - **Purpose:** exposes the selected mode. **Used by:** any view that displays the mode.
- `getWinner()` - **Purpose:** exposes the Game winner. **Used by:** the controller/view to stop additional moves when non-null and display the winning player.
