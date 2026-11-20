# BVA Analysis: `Board`

## Method under test: `getSnapshot()`

**Purpose / use:** Returns an independent representation of the current 8×8 board for Game, controller, and view code.

**Expected behavior:** The returned array has eight rows and eight columns, contains copies of the pieces in the current board state, and cannot be used to mutate the Board's internal array.

### Inputs / state partitions

- board state: newly initialized; later, relocated/captured state;
- square content: `Piece` or `null`;
- coordinates represented in the returned array: `[0, 7]` for rows and columns;
- occupied-square count on a new board: 32;
- piece colors: BLACK and WHITE.

| Test Case ID                                                            | Method(s) under test | State of the System                                                | Expected Output                                                                       | Implemented?       |
|-------------------------------------------------------------------------|----------------------|--------------------------------------------------------------------|---------------------------------------------------------------------------------------|--------------------|
| B1                                                                      | `getSnapshot()`      | Newly initialized `Board`                                          | Exactly 8 rows and 8 columns per row                                                  | :white_check_mark: |
| B2                                                                      | `getSnapshot()`      | Newly initialized `Board`                                          | Boundary positions are correct: `[0][0]` is a BLACK ROOK and `[7][7]` is a WHITE ROOK | :white_check_mark: |
| B3                                                                      | `getSnapshot()`      | Newly initialized `Board`                                          | 32 occupied squares: 16 BLACK, 16 WHITE; 32 empty squares                             | :white_check_mark: |
| B4                                                                      | `getSnapshot()`      | Newly initialized `Board`                                          | All 32 pieces are in their standard starting positions                                | :white_check_mark: |
| B5<br><sub>getSnapshot_ModifyReturnedArray_InternalBoardUnchanged</sub> | `getSnapshot()`      | Obtain snapshot A; set `snapshotA[6][4] = null`; obtain snapshot B | Snapshot B still contains the WHITE PAWN at `[6][4]`                                  | :X:                |
| B6<br><sub>getSnapshot_TwoSnapshots_ReturnDistinctPieceObjects</sub>    | `getSnapshot()`      | Obtain two snapshots from the same Board                           | Corresponding non-null pieces have the same type/color but are distinct objects       | :X:                |

---

## Method under test: `movePiece(Location from, Location to)`

**Purpose / use:** Performs the raw board-state mutation after Game/controller logic has already approved a move.

**Current contract:**

- the source is expected to contain the piece being relocated;
- the destination is expected to be an approved in-board square;
- the source becomes empty;
- the destination receives the moving piece;
- an existing destination piece is replaced, which performs a capture;
- Board does not validate chess legality or switch turns.

| Test Case ID                                                            | Method(s) under test                    | State of the System                                                               | Expected Output                                                    | Implemented?       |
|-------------------------------------------------------------------------|-----------------------------------------|-----------------------------------------------------------------------------------|--------------------------------------------------------------------|--------------------|
| M37<br><sub>movePiece_ToEmptySquare_PieceRelocated</sub>                | `movePiece(Location from, Location to)` | Standard board; move WHITE PAWN `(4,6) → (4,5)`                                   | `[6][4]` is `null`; `[5][4]` is a WHITE PAWN                       | :white_check_mark: |
| M38<br><sub>movePiece_ToEmptySquareTwoRows_PieceRelocated</sub>         | `movePiece(Location from, Location to)` | Standard board; move WHITE PAWN `(4,6) → (4,4)`                                   | `[6][4]` is `null`; `[4][4]` is a WHITE PAWN                       | :white_check_mark: |
| M39<br><sub>movePiece_ToOccupiedSquare_CaptureReplacesDestination</sub> | `movePiece(Location from, Location to)` | After WHITE `(4,6) → (4,4)` and BLACK `(5,1) → (5,3)`; move WHITE `(4,4) → (5,3)` | `[4][4]` is `null`; `[3][5]` is a WHITE PAWN; occupied count is 31 | :white_check_mark: |

## Removed stale API plans

`getPieceAt(...)`, `getValidMovesByPiece(...)`, and `findKing(...)` are not current `Board` methods. They are not active BVA targets. Any future need for them must be reconsidered during the feature that requires them; see [`../../design/future_design_scope.md`](../../design/future_design_scope.md).
