# BVA Analysis: `Piece`

`getType()` and `getColor()` are simple immutable-field getters. Their WHITE/BLACK and type values are already asserted throughout the concrete piece tests; no separate BVA table is required.

`makeCopy()` is abstract and is documented in each concrete piece file.

## Method under test: `toString()`

**Expected behavior:** Returns `<COLOR> <TYPE>` using the enum names.

| Test Case ID                                            | Method(s) under test | State of the System   | Expected Output | Implemented?       |
|---------------------------------------------------------|----------------------|-----------------------|-----------------|--------------------|
| PT1<br><sub>toString_WhiteRook_ReturnColorAndType</sub> | `toString()`         | WHITE ROOK test piece | `"WHITE ROOK"`  | :white_check_mark: |

## Abstract method: `getValidMoves(Location from)`

Concrete piece files document geometric movement BVA. These methods use only:

- the source coordinate;
- the piece's type/color where relevant;
- the board boundary `[0,7]`.

They do not inspect Board occupancy. `Game.getLegalMoves(Location)` currently applies blocker, friendly-destination, and pawn-capture rules.

## Supporting helper tests (not independent BVA cases)

The existing `PieceTest` also exercises two defensive branches of the protected `getDirectionalMoves(...)` helper:

- `null` directions return an empty set;
- a `(0,0)` direction is skipped to prevent a non-progressing loop.

Private/protected helper methods are not assigned BVA IDs because they are implementation details exercised through public behavior or coverage-support tests.
