# Deferred / Future Design Scope

The items below appeared in earlier planning documents or are required for full chess behavior. They are **not current methods**, **not active BVA targets**, and must not be implemented incidentally while finishing basic Move Piece.

## King safety and end-of-game behavior

Potential capabilities:

- determine whether a color is in check;
- filter board-aware moves that leave the moving player's king in check;
- determine checkmate;
- expose a winner after a game-ending condition.

Candidate APIs such as `isInCheck`, `isCheckmate`, `getPlayableMoves`, or `getWinner` must be redesigned and accepted before BVA/TDD begins. In particular, the project should settle whether `getLegalMoves` itself becomes fully legal or whether a separate method remains.

## Special chess moves

Deferred:

- castling and castling rights;
- en passant;
- pawn promotion and promotion choice.

## Game modes

`GameMode` exists but is unused. Integrating Classic versus King of the Hill requires explicit requirements, constructor/UI changes, win rules, BVA, and tests.

## Possible Board queries

Earlier documents proposed `getPieceAt`, `getValidMovesByPiece`, and `findKing`. These methods are not in the current source and are not automatically approved. Reintroduce only if a future feature demonstrates a clear responsibility that cannot be expressed cleanly through the current architecture.

## UI synchronization

Deferred but directly relevant after basic movement works:

- draw selection from `BoardController.getSelectedSquare()`;
- draw `getHighlightedSquares()`;
- repaint after controller state changes;
- update the current-player label after a turn switch;
- display a winner when winner behavior exists.
