# BVA Analysis: `Piece`

## Step 4: Test Cases

### Method under test: `getType()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |

### Method under test: `getColor()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |

### Method under test: `makeCopy()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |

### Method under test: `toString()`

| Test Case ID | State of the System | Expected Output | Implemented? |
|--------------|---------------------|-----------------|--------------|
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |
|              |                     |                 | :x:          |

### Method under test: `getValidMoves()`


### Inputs (for all the getValidMoves methods in each class)

- `from` coordinates: [0,7]
- content of `from`: cases {piece of the active colour, piece of the other colour, empty}
- piece type: cases {PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING}
- pawn start row: cases {on it, off it}
- candidate destination squares: interval [0,7] x [0,7], each case {empty, own piece, opposing piece}
- path: cases {clear, blocked by own piece, blocked by opposing piece}
- only for castling: king, rook {have been moved, have not been moved{

### Outputs

- a set of `Location`: every legal destination
