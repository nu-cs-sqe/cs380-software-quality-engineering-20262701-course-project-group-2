# BVA Analysis for Board

### Method under test: `getSnapshot()`

**Purpose / use:** Gives Game/controller/view code a safe representation of the board for inspection and display.

**Expected behavior:** Returns a snapshot/copy of the current 8x8 board state so callers can inspect the board without directly owning the Board's internal array.

Inputs:
- State Collection: newly initialized chess board
    - The current public interface only allows the standard initialized board state.
    - Empty, exactly one piece, and other board states cannot currently be set.

Outputs:
- Board Collection: fixed 8x8 multidimensional array
- Array Indices: [0, 7]
    - Smallest valid row/column: 0
    - Largest valid row/column: 7
    - -1 and 8 cannot be set through `getSnapshot()`
- Occupied Square Count: 32
- Piece Color Cases: BLACK, WHITE
- Square Content Cases: Piece, empty (`null`)

| Test Case ID | Method(s) under test | State of the System       | Expected Output                                                                                                        | Implemented?       |
|--------------|----------------------|---------------------------|------------------------------------------------------------------------------------------------------------------------|--------------------|
| B1           | `getSnapshot()`      | Newly initialized `Board` | Snapshot contains exactly 8 rows and each row contains exactly 8 columns                                               | :white_check_mark: |
| B2           | `getSnapshot()`      | Newly initialized `Board` | Boundary positions are initialized correctly: `[0][0]` contains a Black Rook and `[7][7]` contains a White Rook        | :white_check_mark: |
| B3           | `getSnapshot()`      | Newly initialized `Board` | Snapshot contains exactly 32 occupied squares: 16 Black pieces and 16 White pieces; the remaining 32 squares are empty | :white_check_mark: |
| B4           | `getSnapshot()`      | Newly initialized `Board` | All 32 pieces are in their standard starting chess positions                                                           | :white_check_mark: |

> Further BVA may be needed as features expand.

---

### Planned method under test: `getPieceAt(Location location)`

**Purpose / use:** Lets Game inspect the piece currently stored on one board square.

**Expected behavior:** Returns the piece at the requested location, or `null` when the square is empty.

Inputs:

Outputs:

| Test Case ID | Method(s) under test            | State of the System | Expected Output | Implemented? |
|--------------|---------------------------------|---------------------|-----------------|--------------|
|              | `getPieceAt(Location location)` |                     |                 | :X:          |

---

### Method under test: `getValidMovesByPiece(Location location)`

**Purpose / use:** Lets Game ask Board for the selected piece's movement-level destinations without passing duplicate type/color information. Board reads the piece at `location` and dispatches to that piece's `getValidMoves()` implementation.

**Expected behavior:** Returns the movement-level / pseudo-legal destinations for the piece currently stored at `location`. This method does not filter moves based on whether the player's own king would be in check afterward.

Inputs:
- location coordinates: interval [0,7] for column and row
- square content: cases {empty, PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING}
- piece color is obtained from the piece on the board
- board state around the piece: cases inherited from the concrete piece movement rules

Outputs:
- a set of `Location`: the movement-level destinations returned by the corresponding piece logic
- set size cases: {empty, exactly 1, 2 or more}

| Test Case ID                                                   | Method(s) under test                      | State of the System                                                                  | Expected Output                                                                                             | Implemented? |
|----------------------------------------------------------------|-------------------------------------------|--------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|--------------|
| M30<br><sub>getValidMovesByPiece_WhitePawn</sub>               | `getValidMovesByPiece(Location location)` | standard starting board, white pawn at (4, 6)                                        | output of [Pawn.getValidMoves](piece/Pawn.md) test M1 `{(4, 5), (4, 4)}`                                    | :X:          |
| M31<br><sub>getValidMovesByPiece_BlackPawn</sub>               | `getValidMovesByPiece(Location location)` | standard starting board, black pawn at (4, 1)                                        | output of [Pawn.getValidMoves](piece/Pawn.md) test M18 `{(4, 2), (4, 3)}`                                   | :X:          |
| M32<br><sub>getValidMovesByPiece_Bishop</sub>                  | `getValidMovesByPiece(Location location)` | after white (3, 6) -> (3, 4); white bishop at (2, 7)                                 | output of [Bishop.getValidMoves](piece/Bishop.md) test M11 `{(3, 6), (4, 5), (5, 4), (6, 3), (7, 2)}`       | :X:          |
| M33<br><sub>getValidMovesByPiece_Knight</sub>                  | `getValidMovesByPiece(Location location)` | standard starting board, white knight at (1, 7)                                      | output of [Knight.getValidMoves](piece/Knight.md) test M13 `{(0, 5), (2, 5)}`                               | :X:          |
| M34<br><sub>getValidMovesByPiece_King</sub>                    | `getValidMovesByPiece(Location location)` | after white (4, 6) -> (4, 4); white king at (4, 7)                                   | output of [King.getValidMoves](piece/King.md) test M15 `{(4, 6)}`                                           | :X:          |
| M35<br><sub>getValidMovesByPiece_Queen</sub>                   | `getValidMovesByPiece(Location location)` | after white (3, 6) -> (3, 4); white queen at (3, 7)                                  | output of [Queen.getValidMoves](piece/Queen.md) test M17 `{(3, 6), (3, 5)}`                                 | :X:          |
| M36<br><sub>getValidMovesByPiece_Rook</sub>                    | `getValidMovesByPiece(Location location)` | after white (7, 6) -> (7, 4) and white knight (6, 7) -> (5, 5); white rook at (7, 7) | output of [Rook.getValidMoves](piece/Rook.md) test M7 `{(7, 6), (7, 5), (6, 7)}`                            | :X:          |
| M47<br><sub>getValidMovesByPiece_BoxedInRook_returnEmpty</sub> | `getValidMovesByPiece(Location location)` | standard starting board, white rook at (0, 7)                                        | `{}` — verifies that an empty destination set is correctly returned through the Board-level dispatch method | :X:          |

---

### Method under test: `movePiece(Location from, Location to)`

**Purpose / use:** Performs the actual board-state mutation after Game/controller logic has already approved a move.

**Expected behavior:** Relocates the piece from `from` to `to`. If `to` contains an opponent piece, that piece is replaced/removed. Board does not decide whether the move is legal.

Inputs:
- `from`: square containing the piece to relocate
- `to`: cases {empty square, square occupied by opponent}

Outputs:
- Board state after relocation
- Source square becomes empty
- Destination contains the moved piece
- If the destination held an opponent piece, that piece is removed

| Test Case ID                                                          | Method(s) under test                    | State of the System                                                                                             | Expected Output                                                                             | Implemented? |
|-----------------------------------------------------------------------|-----------------------------------------|-----------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|--------------|
| M37<br><sub>movePiece_ToEmptySquare_pieceRelocated</sub>              | `movePiece(Location from, Location to)` | standard starting board<br>• from: (4, 6) (white pawn), to: (4, 5)                                              | `snapshot[6][4]` is `null` and `snapshot[5][4]` is a WHITE PAWN                             | :X:          |
| M38<br><sub>movePiece_ToEmptySquareTwoRows_pieceRelocated</sub>       | `movePiece(Location from, Location to)` | standard starting board<br>• from: (4, 6) (white pawn), to: (4, 4)                                              | `snapshot[6][4]` is `null`, `snapshot[4][4]` is a WHITE PAWN                                | :X:          |
| M39<br><sub>movePiece_ToOpponentPiece_captureReplacesAndRemoves</sub> | `movePiece(Location from, Location to)` | after white (4, 6) -> (4, 4) and black (5, 1) -> (5, 3)<br>• from: (4, 4) (white pawn), to: (5, 3) (black pawn) | `snapshot[4][4]` is `null`, `snapshot[3][5]` is a WHITE PAWN, and the black pawn is removed | :X:          |

---

### Planned method under test: `findKing(PieceColor color)`

**Purpose / use:** Lets Game locate a player's king when later checking check/checkmate and king safety.

**Expected behavior:** Returns the current `Location` of the requested color's king.

Inputs:

Outputs:

| Test Case ID | Method(s) under test         | State of the System | Expected Output | Implemented? |
|--------------|------------------------------|---------------------|-----------------|--------------|
|              | `findKing(PieceColor color)` |                     |                 | :X:          |
