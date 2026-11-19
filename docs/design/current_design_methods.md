# Current Design Methods

This document reflects the attached source tree. Methods listed as incomplete exist in source but do not yet provide their intended behavior.

## `Main`

- `main(String[] args)` — schedules `WelcomeView` creation on the Swing event-dispatch thread.

## `domain.Location`

- `getColumn()` / `getRow()` — return the stored coordinates.
- `equals(Object)` / `hashCode()` — provide coordinate-based equality for sets and highlighted-square membership.

## `domain.Board`

- `getSnapshot()` — returns an 8×8 array containing copies of the current pieces.
- `movePiece(Location from, Location to)` — performs raw relocation/replacement after a move has already been approved.

Board owns the mutable `Piece[][]`. It does not determine legal moves or switch turns.

## `domain.Game`

- `Game()` — creates a standard Board and starts with WHITE.
- `Game(Board board)` — package-private dependency injection used by tests.
- `getCurrentTurn()` — returns WHITE or BLACK.
- `getBoardSnapshot()` — delegates to Board.
- `getLegalMoves(Location from)` — returns current board-aware moves: piece geometry plus blockers/friendly-destination/pawn-capture rules. It does not yet enforce king safety.
- `movePiece(Location from, Location to)` — **incomplete**; currently throws `UnsupportedOperationException`.
- `switchTurn()` — toggles WHITE/BLACK.

## `domain.piece.Piece`

- `getType()` / `getColor()` — immutable piece metadata.
- `makeCopy()` — abstract same-type/same-color copy.
- `getValidMoves(Location from)` — abstract geometric move generation.
- `toString()` — `<COLOR> <TYPE>`.
- `getDirectionalMoves(...)` / `isInsideBoard(...)` — protected helpers.

`Pawn`, `Knight`, `Bishop`, `Rook`, `Queen`, and `King` implement `makeCopy()` and `getValidMoves(Location)`.

## `controller.BoardController`

- `handleSquareClick(Location)` — handles selection, reselection, deselection, and approved-move delegation.
- `getSelectedSquare()` — current controller selection or `null`.
- `getHighlightedSquares()` — current Game-reported destinations.
- `getCurrentTurn()` — delegates to Game.
- `getBoardSnapshot()` — delegates to Game.

## Views

- `WelcomeView` validates player names and creates `MainView`.
- `MainView` creates one Game, BoardController, BoardView, and GameStatsView.
- `BoardView` draws the board/pieces and forwards clicks. It currently keeps local click-selection state and does not draw controller highlights.
- `GameStatsView` displays player names and the current player only at construction time.

## `domain.GameMode`

The enum exists with `CLASSIC` and `KING_OF_THE_HILL`, but it is not connected to Game or the UI in the current source tree.
