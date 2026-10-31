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

### Method under test: `getValidMoves(pieceColor currentTurn, pieceType type, Location location)`
bruh i realize according to uncle bob we're nly supposed to hvae <=3 arguments
could also do `getValidMoves(pieceColor currentTurn, Location location)` and infer the piece type?

#### Pawn

- **M1: getValidMoves_Pawn_OnStartRow_returnTwo** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (4, 6) (white pawn, still on its start row, both adjacent diagonals empty)
    - **Expected output**: `{(4, 5), (4, 4)}` 
  
- **M2: getValidMoves_Pawn_OffStartRow_returnOne** ( :x: )
    - **State of the system**:
      - after M1's white move (4, 6) -> (4, 4)
      - from: (4, 4) (white pawn, past its start row; (4, 3) empty)
    - **Expected output**: `{(4, 3)}` 

- **M3: getValidMoves_Pawn_BlockedAhead_returnEmpty** ( :x: )
    - **State of the system**:
      - after white (4, 6) -> (4, 4) and black (4, 1) -> (4, 3)
      - from: (4, 4) (white pawn)
    - **Expected output**: `{}`
  
- **M4: getValidMoves_Pawn_OneCapturePossible_returnTwo** ( :x: )
    - **State of the system**:
      - after white (4, 6) -> (4, 4) and black (5, 1) -> (5, 3)
      - from: (4, 4) (white pawn)
    - **Expected output**: `{(4, 3), (5, 3)}` 

- **M5: getValidMoves_Pawn_TwoCapturesPossible_returnThree** ( :x: )
    - **State of the system**:
      - after white (4, 6) -> (4, 4), black (3, 1) -> (3, 3) and black (5, 1) -> (5, 3)
      - from: (4, 4) (white pawn)
    - **Expected output**: `{(4, 3), (3, 3), (5, 3)}`

this test is necessary to check that a black pawn is moving in the opposite direction as white pawns correctly
- **M18: getValidMoves_Pawn_Black_returnTwo** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (4, 1) (black pawn, still on its start row, both adjacent diagonals empty)
    - **Expected output**: `{(4, 2), (4, 3)}` — the mirror of M1: a black pawn advances towards row 7, so its destinations have a larger row than `from`, never a smaller one. The empty set, and a set containing `(4, 0)`, both fail.

#### Rook

- **M6: getValidMoves_Rook_returnEmpty** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (0, 7) (white rook, own pawn on (0, 6) and own knight on (1, 7))
    - **Expected output**: `{}` 
  
- **M7: getValidMoves_Rook_AdjacentTilesUnblocked_returnAdjacent** ( :x: )
    - **State of the system**:
      - after white (7, 6) -> (7, 4) and white knight (6, 7) -> (5, 5)
      - from: (7, 7) (white rook)
    - **Expected output**: `{(7, 6), (7, 5), (6, 7)}`

- **M8: getValidMoves_Rook_AllTilesUnblocked_returnAllRowAndColumn** ( :x: )
    - **State of the system**:
        - just the rook on (7, 7) (white rook) i know this isn't possible
    - **Expected output**: `{(x, x) for x in range(0,7)}` sorry if this isn't valid output format

- **M9: getValidMoves_Rook_BlockedByOpponent_returnUpToOpponent** ( :x: )
    - **State of the system**:
      - after black (7, 1) -> (7, 3), white (7, 6) -> (7, 5), black pawn (7,2)->(7,4), white (7, 5) -> (7, 4)
      - from: (7, 7) (white rook, black pawn on (7, 3))
    - **Expected output**: `{(7, 6), (7, 5), (7, 4), (7, 3)}`
  
#### Bishop

- **M10: getValidMoves_Bishop_Blocked_returnEmpty** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (2, 7) (white bishop, own pawns on (1, 6) and (3, 6))
    - **Expected output**: `{}` 
  
- **M11: getValidMoves_Bishop_OpenDiagonal_returnDiagonal** ( :x: )
    - **State of the system**:
      - after white (3, 6) -> (3, 4)
      - from: (2, 7) (white bishop)
    - **Expected output**: `{(3, 6), (4, 5), (5, 4), (6, 3), (7, 2)}` 

- **M12: getValidMoves_Bishop_OpponentInDiagonal_returnUpToOpponent** ( :x: )
    - **State of the system**:
      - after white (3, 6) -> (3, 4), black (4, 1) -> (4, 3), black (4, 3) -> (4, 4), black (4, 4) -> (4, 5)
      - from: (2, 7) (white bishop; its diagonal now runs through the empty (3, 6) into the black pawn on (4, 5))
    - **Expected output**: `{(3, 6), (4, 5)}` — the same shape as M9: the empty square before the opponent is included, the opponent's own square is included (it is a capture), and nothing behind it is, so `(5, 4)`, `(6, 3)` and `(7, 2)` are excluded. Stops at the opponent rather than at the board edge, which is what separates this from M11.


#### Knight

- **M13: getValidMoves_Knight_PathBlocked_returnTwo** ( :x: )
    - **State of the system**:
      - standard starting board, knight surrounded by its own pawn and rook
      - from: (1, 7) (white knight)
    - **Expected output**: `{(0, 5), (2, 5)}` exactly — (3, 6) holds an own pawn and is excluded, (1, 5) is a straight line and is excluded, and the two included squares are reached through blockers

- **M19: getValidMoves_Knight_NoValidDestinations_returnEmpty** ( :x: )
    - **State of the system**:
      - a white knight on (0, 0), a white rook on (1, 2) and a white bishop on (2, 1), every other square empty
      - from: (0, 0) white knight in the corner
    - **Expected output**: `{}` 

- **M20: getValidMoves_Knight_OpponentOnDestination_returnIncludingOpponent** ( :x: )
    - **State of the system**:
      - after white (3, 6) -> (3, 4), black (0, 1) -> (0, 3), black (0, 3) -> (0, 4), black (0, 4) -> (0, 5)
      - from: (1, 7) (white knight; (0, 5) holds a black pawn, (2, 5) and (3, 6) are empty)
    - **Expected output**: `{(0, 5), (2, 5), (3, 6)}` 

- **M25: getValidMoves_Knight_AllDirectionsFromCentre_returnEight** ( :x: )
    - **State of the system**:
      - a single white knight on (3, 3), every other square empty
      - from: (3, 3) 
    - **Expected output**: `{(1, 2), (1, 4), (2, 1), (2, 5), (4, 1), (4, 5), (5, 2), (5, 4)}` 

#### King

- **M14: getValidMoves_King_BoxedIn_returnEmpty** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (4, 7) (white king, all five in-board neighbours hold own pieces)
    - **Expected output**: `{}` — size 0 on the standard board

- **M15: getValidMoves_King_OneNeighbourFreed_returnOne** ( :x: )
    - **State of the system**:
      - after white (4, 6) -> (4, 4)
      - from: (4, 7) (white king; (4, 6) now empty, (4, 5) also empty)
    - **Expected output**: `{(4, 6)}` 

- **M21: getValidMoves_King_OpponentAdjacent_returnIncludingOpponent** ( :x: )
    - **State of the system**:
      - after black (5, 1) -> (5, 3), black (5, 3) -> (5, 4), black (5, 4) -> (5, 5), black (5, 5) captures white (4, 6)
      - from: (4, 7) (white king; (4, 6) now holds a black pawn, (3, 6) and (5, 6) hold own pawns, (3, 7) and (5, 7) hold own bishops)
    - **Expected output**: `{(4, 6)}` 

- **M22: getValidMoves_King_AllDirectionsFromCentre_returnEight** ( :x: )
    - **State of the system**:
      - single white king on (3, 3), every other square empty
      - from: (3, 3) (white king, not on an edge, nothing at all to block it)
    - **Expected output**: `{(2, 2), (2, 3), (2, 4), (3, 2), (3, 4), (4, 2), (4, 3), (4, 4)}`

- **M26: getValidMoves_King_RightCastlePathClear_returnIncludingCastle** ( :x: )
    - **State of the system**:
      - neither rook nor king has been moved yet
      - after white (4, 6) -> (4, 4), black (7, 1) -> (7, 3), white (5, 7) -> (4, 6) (bishop off f1), black (7, 3) -> (7, 4), white (6, 7) -> (5, 5) (knight off g1)
      - from: (4, 7) (white king; f1 (5, 7) and g1 (6, 7) are empty, own rook still on h1 (7, 7), own pieces still on d1 (3, 7), d2 (3, 6), e2 (4, 6) and f2 (5, 6))
    - **Expected output**: `{(5, 7), (6, 7)}` 
  
- **M27: getValidMoves_King_LeftCastlePathClear_returnIncludingCastle** ( :x: )
    - **State of the system**:
      - neither rook nor king has been moved yet
      - after white (3, 6) -> (3, 4) (pawn off d2), black (6, 1) -> (6, 3), white (1, 6) -> (1, 4) (pawn off b2), black (6, 3) -> (6, 4), white (2, 7) -> (0, 5) (bishop off c1), black (6, 4) -> (6, 5), white (1, 7) -> (2, 5) (knight off b1), black (5, 1) -> (5, 3), white (3, 7) -> (3, 6) (queen off d1), black (5, 3) -> (5, 4)
      - from: (4, 7) (white king; b1 (1, 7), c1 (2, 7) and d1 (3, 7) are empty, own rook still on a1 (0, 7), own bishop still on f1 (5, 7))
    - **Expected output**: `{(3, 7), (2, 7)}` 
  
- **M28: getValidMoves_King_RightCastlePathClear_KingMoved_NoCastle** ( :x: )
    - **State of the system**:
        - king has been moved
        - same setup as M26 but king has also moved one square to right
    - **Expected output**: `{(5, 7)}`

- **M29: getValidMoves_King_RightCastlePathClear_RookMoved_NoCastle** ( :x: )
    - **State of the system**:
        - rook has been moved
        - same setup as M26 but rook has also moved one square to left
    - **Expected output**: `{(5, 7)}`

  
#### Queen

- **M16: getValidMoves_Queen_BoxedIn_returnEmpty** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (3, 7) (white queen, own pieces on (3, 6), (2, 6), (4, 6), (2, 7) and (4, 7))
    - **Expected output**: `{}` — every in-board direction is blocked by a friendly piece

- **M17: getValidMoves_Queen_AdjacentsCleared_returnAdjacent** ( :x: )
    - **State of the system**:
      - after white (3, 6) -> (3, 4)
      - from: (3, 7) (white queen)
    - **Expected output**: `{(3, 6), (3, 5)}` exactly — the cleared file up to the own pawn on (3, 4); the diagonals are still blocked by own pawns, so the queen's combined sliding directions add nothing here

- **M23: getValidMoves_Queen_OpponentInFile_returnUpToOpponent** ( :x: )
    - **State of the system**:
      - after black (4, 1) -> (4, 3), black (4, 3) -> (4, 4), black (4, 4) -> (4, 5), white (3, 6) captures (4, 5), black (3, 1) -> (3, 3)
      - from: (3, 7) (white queen; the file below it is empty down to the black pawn on (3, 3), and (2, 6) and (4, 6) still hold own pawns)
    - **Expected output**: `{(3, 6), (3, 5), (3, 4), (3, 3)}` 
  
- **M24: getValidMoves_Queen_AllDirectionsFromCentre_returnTwentySeven** ( :x: )
  - **State of the system**:
    - single white queen on (3, 3), every other square empty
    - from: (3, 3) (white queen, not on an edge, nothing at all to block it)
  - **Expected output**: 
    - row 3: `(0, 3), (1, 3), (2, 3), (4, 3), (5, 3), (6, 3), (7, 3)` (7)
    - column 3: `(3, 0), (3, 1), (3, 2), (3, 4), (3, 5), (3, 6), (3, 7)` (7)
    - diagonal 1: `(0, 0), (1, 1), (2, 2), (4, 4), (5, 5), (6, 6), (7, 7)` (7)
    - diagonal 2: `(0, 6), (1, 5), (2, 4), (4, 2), (5, 1), (6, 0)` (6)
 


> BTW NOT COVERED - en passant, promotion, the "leaves own king in check" rule, and the moved-yet tracking that revokes castling rights. M26 and M27 assume the pieces are where they are regardless of how they got there; M28 and M29 are the cases that need a has-moved flag, so they cannot pass until that flag exists.


## BVA/thinking process documentation: `Board.getValidMoves(pieceColor currentTurn, pieceType type, Location location)`

### Inputs

- `from` coordinates: [0,7]
- content of `from`: cases {piece of the active colour, piece of the other colour, empty}
- piece type: cases {PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING}
- piece color: cases {white, black}
- pawn start row: cases {on it, off it}
- candidate destination squares: interval [0,7] x [0,7], each case {empty, own piece, opposing piece}
- path: cases {clear, blocked by own piece, blocked by opposing piece}
- only for castling: king, rook {have been moved, have not been moved{

### Outputs

- a set of `Location`: every legal destination

---

### Method under test: `movePiece(Location from, Location to)`


#### Relocation

- **E1: movePiece_ToEmptySquare_pieceRelocated** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (4, 6) (white pawn), to: (4, 5)
    - **Expected output**: `snapshot[6][4]` is `null` and `snapshot[5][4]` is a WHITE PAWN
  
- **E2: movePiece_ToEmptySquareTwoRows_pieceRelocated** ( :x: )
    - **State of the system**:
      - standard starting board
      - from: (4, 6) (white pawn), to: (4, 4)
    - **Expected output**: `snapshot[6][4]` is `null`, `snapshot[4][4]` is a WHITE PAWN
  
- **E3: movePiece_ToOpponentPiece_captureReplacesAndRemoves** ( :x: )
    - **State of the system**:
      - after white (4, 6) -> (4, 4) and black (5, 1) -> (5, 3)
      - from: (4, 4) (white pawn), to: (5, 3) (black pawn)
    - **Expected output**: `snapshot[4][4]` is `null`, `snapshot[3][5]` is a WHITE PAWN, the black pawn is DEAD

- **E4 movePiece_KingTwoSquares_rookAlsoRelocated** ( :x: )
    - **State of the system**:
      - setup of m26
    - **Expected output**: `snapshot[7][4]` is `null`, `snapshot[7][6]` is a WHITE KING, `snapshot[7][7]` is `null` and `snapshot[7][5]` is a WHITE ROOK
---
