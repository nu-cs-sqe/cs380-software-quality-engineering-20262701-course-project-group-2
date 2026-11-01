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

**Expected behavior:** Returns every movement-level destination the piece is allowed to make from its current square based on that piece's movement rules, board boundaries, blockers, and captures. It does **not** remove moves because they would leave the player's own king in check; that filtering belongs to `Game`.

`getValidMoves(...)` is analyzed in each concrete piece class.

### Common inputs for the concrete `getValidMoves(...)` methods

- `from` coordinates: interval [0,7] for column and row
- candidate destination squares: interval [0,7] x [0,7]
- candidate destination content: cases {empty, own piece, opposing piece}
- path for sliding pieces: cases {clear, blocked by own piece, blocked by opposing piece}
- pawn start row: cases {on it, off it}

### Common output

- a set of `Location`: every destination the piece is allowed to move to based on that piece's movement rules, board boundaries, blockers, and captures

### Scope note

These piece-level methods do **not** filter moves based on whether the move would leave the player's own king in check or move the king into check. Those additional legality checks belong at the `Game` level.

