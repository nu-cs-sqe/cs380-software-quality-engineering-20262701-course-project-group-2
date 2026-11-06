# BVA Analysis: `Piece`

### Method under test: `getType()`

**Purpose / use:** Lets Board/Game code identify what kind of chess piece this object represents.

**Expected behavior:** Returns the `PieceType` stored by the piece.

Inputs:
- Piece type: cases {PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING}

Outputs:
- The corresponding `PieceType`

The case coverage for each concrete type is exercised through the concrete piece classes and their movement tests.

#### Step 4: Planned test cases

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `getType()`          |                     |                 | :X:          |

### Method under test: `getColor()`

**Purpose / use:** Lets turn, capture, and opponent logic determine whether the piece is WHITE or BLACK.

**Expected behavior:** Returns the `PieceColor` stored by the piece.

Inputs:
- Piece color: cases {WHITE, BLACK}

Outputs:
- The corresponding `PieceColor`

The WHITE and BLACK cases are exercised through the concrete piece classes.

#### Step 4: Planned test cases

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `getColor()`         |                     |                 | :X:          |
|              | `getColor()`         |                     |                 | :X:          |

---

### Method under test: `makeCopy()`

**Purpose / use:** Creates an independent piece object for board snapshots or simulated board states so changing the copy does not change the original piece.

**Expected behavior:** Creates a distinct piece object with the same piece type and color as the original. `makeCopy()` is abstract in `Piece`; concrete BVA cases are documented in each concrete piece class.

### Method under test: `toString()`

**Purpose / use:** Provides a readable text form of a piece for debugging or simple text display.

**Expected behavior:** Returns a readable string representation containing the piece color and type.

Inputs / object state:
- Piece color: cases {WHITE, BLACK}
- Piece type: cases {PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING}

Outputs:
- String representation containing the piece color and type

No additional boundary-specific cases are needed beyond the color/type cases above.

#### Step 4: Planned test cases

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `toString()`         |                     |                 | :X:          |

### Method under test: `getValidMoves(...)`

**Purpose / use:** Provides the first stage of move generation. Board asks the concrete piece for the squares it can reach according to that piece's own movement rules, then Game applies additional legality rules.

**Expected behavior:** Returns the squares the piece's own movement pattern reaches from `from`, clipped to the board. It reads **no other square**: the result is the same whatever else stands on the board, so there is no stopping at a blocker, no own-piece exclusion, and no capture square. Everything that depends on other pieces — blockers, captures, whether a move leaves the player's own king in check, and castling — is applied by [`Game.getLegalMoves()`](../Game.md).

`getValidMoves(...)` is analyzed in each concrete piece class.

### Common inputs for the concrete `getValidMoves(...)` methods

- `from` coordinates: interval [0,7] for column and row
- `from` position on the board: cases {interior, edge, corner}
- pawn start row: cases {on it, off it}
- board content: none — no other square is read, which is the point of the boundary above

### Common output

- a set of `Location`: every square the piece's movement pattern reaches from `from`, clipped to the board

### Scope note

These piece-level methods do **not** look at other pieces at all. Blockers, captures, moves that would leave the player's own king in check, and castling are all decided at the `Game` level by [`Game.getLegalMoves()`](../Game.md). Cases that were originally written for `getValidMoves()` but depended on other pieces have been moved there with their IDs unchanged.

