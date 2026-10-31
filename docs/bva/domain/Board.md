# BVA Analysis for Board

### Method under test: `getSnapshot()`

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

> Further BVA may be needed as features expand. This is preliminary only for the game setup feature.

---

### Method under test: `Board.getValidMovesByPiece(Location location, PieceType type, PieceColor color)`
this may be too many arguments according to uncle bob
the piece color is ONLY needed for the pawn since they move in opposite directions
not sure if need to test returning an empty set? thoughts? 

- **M30: getValidMovesByPiece_WhitePawn** ( :x: )
  - **State of the system**: standard starting board, white pawn at (4, 6); type: `PAWN`; color: `WHITE`
  - **Expected output**: output of [Pawn.getValidMoves](piece/Pawn.md) test M1 `{(4, 5), (4, 4)}`

- **M31: getValidMovesByPiece_BlackPawn** ( :x: )
  - **State of the system**: standard starting board, black pawn at (4, 1); type: `PAWN`; color: `BLACK`
  - **Expected output**: output of [Pawn.getValidMoves](piece/Pawn.md) test M18 `{(4, 2), (4, 3)}`
  
- **M32: getValidMovesByPiece_Bishop** ( :x: )
  - **State of the system**: after white (3, 6) -> (3, 4); white bishop at (2, 7); type: `BISHOP`; color: `WHITE`
  - **Expected output**: output of [Bishop.getValidMoves](piece/Bishop.md) test M11 `{(3, 6), (4, 5), (5, 4), (6, 3), (7, 2)}`

- **M33: getValidMovesByPiece_Knight** ( :x: )
  - **State of the system**: standard starting board, white knight at (1, 7); type: `KNIGHT`; color: `WHITE`
  - **Expected output**: output of [Knight.getValidMoves](piece/Knight.md) test M13 `{(0, 5), (2, 5)}`
  
- **M34: getValidMovesByPiece_King** ( :x: )
  - **State of the system**: after white (4, 6) -> (4, 4); white king at (4, 7); type: `KING`; color: `WHITE`
  - **Expected output**: output of [King.getValidMoves](piece/King.md) test M15 `{(4, 6)}`
  
- **M35: getValidMovesByPiece_Queen** ( :x: )
  - **State of the system**: after white (3, 6) -> (3, 4); white queen at (3, 7); type: `QUEEN`; color: `WHITE`
  - **Expected output**: output of [Queen.getValidMoves](piece/Queen.md) test M17 `{(3, 6), (3, 5)}`
  
- **M36: getValidMovesByPiece_Rook** ( :x: )
  - **State of the system**: after white (7, 6) -> (7, 4) and white knight (6, 7) -> (5, 5); white rook at (7, 7); type: `ROOK`; color: `WHITE`
  - **Expected output**: output of [Rook.getValidMoves](piece/Rook.md) test M7  `{(7, 6), (7, 5), (6, 7)}`
  
---

### Method under test: `movePiece(Location from, Location to)`

- **M37: movePiece_ToEmptySquare_pieceRelocated** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (4, 6) (white pawn), to: (4, 5)
    - **Expected output**: `snapshot[6][4]` is `null` and `snapshot[5][4]` is a WHITE PAWN
  
- **M38: movePiece_ToEmptySquareTwoRows_pieceRelocated** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (4, 6) (white pawn), to: (4, 4)
    - **Expected output**: `snapshot[6][4]` is `null`, `snapshot[4][4]` is a WHITE PAWN
  
- **M39: movePiece_ToOpponentPiece_captureReplacesAndRemoves** ( :x: )
    - **State of the system**:
      - after white (4, 6) -> (4, 4) and black (5, 1) -> (5, 3)
      - from: (4, 4) (white pawn), to: (5, 3) (black pawn)
    - **Expected output**: `snapshot[4][4]` is `null`, `snapshot[3][5]` is a WHITE PAWN, the black pawn is DEAD

- **E4 movePiece_KingTwoSquares_rookAlsoRelocated** ( :x: )
    - **State of the system**:
      - setup of m26
    - **Expected output**: `snapshot[7][4]` is `null`, `snapshot[7][6]` is a WHITE KING, `snapshot[7][7]` is `null` and `snapshot[7][5]` is a WHITE ROOK
---
