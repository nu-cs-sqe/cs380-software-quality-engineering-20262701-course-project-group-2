# BVA Analysis: `Game`

## Current scope

`Game` owns the Board, the current turn, and board-aware move generation. The current `getLegalMoves(Location)` implementation filters geometric moves for blockers, friendly destinations, pawn forward occupancy, and pawn captures. It does not yet filter moves that expose the player's king.

`Game(Board)` is package-private dependency injection used by tests; constructor BVA is not required.

## Method under test: `getCurrentTurn()`

| Test Case ID                                        | Method(s) under test | State of the System    | Expected Output    | Implemented?       |
|-----------------------------------------------------|----------------------|------------------------|--------------------|--------------------|
| G1<br><sub>getCurrentTurn_NewGame_ReturnWhite</sub> | `getCurrentTurn()`   | Newly initialized Game | `PieceColor.WHITE` | :white_check_mark: |

---

## Method under test: `getBoardSnapshot()`

**Purpose / use:** Delegates access to the current Board snapshot so controllers/views do not own Board directly.

| Test Case ID                                                   | Method(s) under test | State of the System                      | Expected Output                                         | Implemented?       |
|----------------------------------------------------------------|----------------------|------------------------------------------|---------------------------------------------------------|--------------------|
| G5<br><sub>getBoardSnapshot_NewGame_Return8x8Snapshot</sub>    | `getBoardSnapshot()` | Newly initialized Game                   | Non-null snapshot with 8 rows and 8 columns per row     | :white_check_mark: |
| G6<br><sub>getBoardSnapshot_AfterMove_ReturnUpdatedBoard</sub> | `getBoardSnapshot()` | New Game; call `movePiece((4,6), (4,5))` | Source is empty and destination contains the WHITE PAWN | :X:                |

---

## Method under test: `getLegalMoves(Location from)`

**Purpose / use:** Produces board-aware destinations for the piece at `from`.

**Precondition:** `from` is an in-board square containing a piece. Invalid/empty input behavior is not currently defined and must not be invented as part of the basic Move Piece implementation.

**Output:** An unmodifiable `Set<Location>` containing geometric destinations after current occupancy rules are applied.

### Pawn cases

| Test Case ID                                                          | Method(s) under test           | State of the System                                                        | Expected Output                                  | Implemented?       |
|-----------------------------------------------------------------------|--------------------------------|----------------------------------------------------------------------------|--------------------------------------------------|--------------------|
| M3<br><sub>getLegalMoves_Pawn_FriendlyPieceAhead_ReturnEmpty</sub>    | `getLegalMoves(Location from)` | WHITE pawn `(4,4)`; WHITE piece `(4,3)`                                    | `{}`                                             | :white_check_mark: |
| M4<br><sub>getLegalMoves_Pawn_OneCapturePossible_ReturnTwo</sub>      | `getLegalMoves(Location from)` | WHITE pawn `(4,4)`; BLACK piece `(5,3)`; forward square empty              | `{(4,3), (5,3)}`                                 | :white_check_mark: |
| M5<br><sub>getLegalMoves_Pawn_TwoCapturesPossible_ReturnThree</sub>   | `getLegalMoves(Location from)` | WHITE pawn `(4,4)`; BLACK pieces `(3,3)` and `(5,3)`; forward square empty | `{(4,3), (3,3), (5,3)}`                          | :white_check_mark: |
| M40<br><sub>getLegalMoves_Pawn_StartRowBlockedAhead_ReturnEmpty</sub> | `getLegalMoves(Location from)` | WHITE pawn `(4,6)`; occupied `(4,5)`; `(4,4)` empty                        | `{}`; pawn cannot enter or jump over the blocker | :white_check_mark: |
| M41<br><sub>getLegalMoves_Pawn_FriendlyPieceDiagonal_NotCapture</sub> | `getLegalMoves(Location from)` | WHITE pawn `(4,4)`; WHITE piece `(5,3)`; forward empty                     | `{(4,3)}`                                        | :white_check_mark: |
| M42<br><sub>getLegalMoves_Pawn_OpponentAhead_NotCapture</sub>         | `getLegalMoves(Location from)` | WHITE pawn `(4,4)`; BLACK piece `(4,3)`; diagonals empty                   | `{}`; pawn cannot capture forward                | :white_check_mark: |
| M43<br><sub>getLegalMoves_Pawn_LeftEdge_NoOffBoardDiagonal</sub>      | `getLegalMoves(Location from)` | WHITE pawn `(0,4)`; BLACK piece `(1,3)`                                    | `{(0,3), (1,3)}`; no column `-1`                 | :white_check_mark: |
| M82<br><sub>getLegalMoves_Pawn_RightEdge_NoOffBoardDiagonal</sub>     | `getLegalMoves(Location from)` | WHITE pawn `(7,4)`; BLACK piece `(6,3)`                                    | `{(7,3), (6,3)}`; no column `8`                  | :white_check_mark: |
| M83<br><sub>getLegalMoves_WhitePawn_LastRank_ReturnEmpty</sub>        | `getLegalMoves(Location from)` | WHITE pawn `(4,0)`                                                         | `{}`                                             | :white_check_mark: |
| M84<br><sub>getLegalMoves_BlackPawn_LastRank_ReturnEmpty</sub>        | `getLegalMoves(Location from)` | BLACK pawn `(4,7)`                                                         | `{}`                                             | :white_check_mark: |

### Knight cases

| Test Case ID                                                                         | Method(s) under test           | State of the System                                                           | Expected Output                                            | Implemented?       |
|--------------------------------------------------------------------------------------|--------------------------------|-------------------------------------------------------------------------------|------------------------------------------------------------|--------------------|
| M13<br><sub>getLegalMoves_Knight_JumpsOverBlockers_ExcludesFriendlyDestination</sub> | `getLegalMoves(Location from)` | Standard board; WHITE knight `(1,7)`                                          | `{(0,5), (2,5)}`; own pawn at `(3,6)` excluded             | :white_check_mark: |
| M19<br><sub>getLegalMoves_Knight_AllDestinationsFriendly_ReturnEmpty</sub>           | `getLegalMoves(Location from)` | WHITE knight `(0,0)`; WHITE pieces `(1,2)` and `(2,1)`                        | `{}`                                                       | :white_check_mark: |
| M20<br><sub>getLegalMoves_Knight_OpponentDestination_Included</sub>                  | `getLegalMoves(Location from)` | WHITE knight `(3,4)`; BLACK pawn `(5,3)`; all other knight destinations empty | `{(1,3), (1,5), (2,2), (2,6), (4,2), (4,6), (5,3), (5,5)}` | :white_check_mark: |

### Sliding-piece cases

| Test Case ID                                                                 | Method(s) under test           | State of the System                                                             | Expected Output                                                      | Implemented?       |
|------------------------------------------------------------------------------|--------------------------------|---------------------------------------------------------------------------------|----------------------------------------------------------------------|--------------------|
| M10<br><sub>getLegalMoves_Bishop_FriendlyBlockers_ReturnEmpty</sub>          | `getLegalMoves(Location from)` | WHITE bishop `(2,7)`; WHITE pieces `(1,6)` and `(3,6)`                          | `{}`                                                                 | :white_check_mark: |
| M12<br><sub>getLegalMoves_Bishop_OpponentOnDiagonal_ReturnUpToOpponent</sub> | `getLegalMoves(Location from)` | WHITE bishop `(2,7)`; BLACK pawn `(4,5)`; other squares empty                   | `{(0,5), (1,6), (3,6), (4,5)}`; opponent included, nothing beyond it | :white_check_mark: |
| M6<br><sub>getLegalMoves_Rook_FriendlyBlockers_ReturnEmpty</sub>             | `getLegalMoves(Location from)` | WHITE rook `(0,7)`; WHITE pieces `(0,6)` and `(1,7)`                            | `{}`                                                                 | :white_check_mark: |
| M7<br><sub>getLegalMoves_Rook_PartiallyOpen_ReturnUntilFriendlyBlocker</sub> | `getLegalMoves(Location from)` | Standard board modified so `(7,6)` and `(6,7)` are empty; WHITE blocker `(7,4)` | `{(7,6), (7,5), (6,7)}`                                              | :white_check_mark: |
| M9<br><sub>getLegalMoves_Rook_OpponentBlocker_ReturnUpToOpponent</sub>       | `getLegalMoves(Location from)` | WHITE rook `(7,7)`; BLACK piece `(7,3)`; WHITE piece `(6,7)`                    | `{(7,6), (7,5), (7,4), (7,3)}`                                       | :white_check_mark: |
| M16<br><sub>getLegalMoves_Queen_BoxedIn_ReturnEmpty</sub>                    | `getLegalMoves(Location from)` | Standard board; WHITE queen `(3,7)`                                             | `{}`                                                                 | :white_check_mark: |
| M17<br><sub>getLegalMoves_Queen_FilePartiallyOpened_ReturnTwo</sub>          | `getLegalMoves(Location from)` | Standard board; clear `(3,6)`; WHITE blocker `(3,4)`                            | `{(3,6), (3,5)}`                                                     | :white_check_mark: |
| M23<br><sub>getLegalMoves_Queen_OpponentInFile_ReturnUpToOpponent</sub>      | `getLegalMoves(Location from)` | Standard board; clear `(3,6)`; BLACK pawn `(3,3)`                               | `{(3,6), (3,5), (3,4), (3,3)}`                                       | :white_check_mark: |

### King cases under the current scope

These cases apply occupancy rules only. They do not yet test attacked-square/king-safety restrictions.

| Test Case ID                                                        | Method(s) under test           | State of the System                                               | Expected Output | Implemented?       |
|---------------------------------------------------------------------|--------------------------------|-------------------------------------------------------------------|-----------------|--------------------|
| M14<br><sub>getLegalMoves_King_FriendlyNeighbours_ReturnEmpty</sub> | `getLegalMoves(Location from)` | Standard board; WHITE king `(4,7)`                                | `{}`            | :white_check_mark: |
| M15<br><sub>getLegalMoves_King_OneNeighbourFreed_ReturnOne</sub>    | `getLegalMoves(Location from)` | Standard board; clear `(4,6)` and place the moved pawn at `(4,4)` | `{(4,6)}`       | :white_check_mark: |
| M21<br><sub>getLegalMoves_King_OpponentAdjacent_Included</sub>      | `getLegalMoves(Location from)` | Standard board with BLACK pawn at `(4,6)`                         | `{(4,6)}`       | :white_check_mark: |

---

## Method under test: `movePiece(Location from, Location to)`

**Purpose / use:** Delegates an already-approved relocation to Board. It must not switch the turn by itself.

| Test Case ID                                                       | Method(s) under test                    | State of the System                            | Expected Output                                                    | Implemented? |
|--------------------------------------------------------------------|-----------------------------------------|------------------------------------------------|--------------------------------------------------------------------|--------------|
| G2<br><sub>movePiece_ApprovedMove_DelegateToBoardAndKeepTurn</sub> | `movePiece(Location from, Location to)` | Inject mocked Board; new Game; `(4,6) → (4,4)` | `Board.movePiece(from,to)` called once; current turn remains WHITE | :X:          |

---

## Method under test: `switchTurn()`

| Test Case ID                                  | Method(s) under test | State of the System                           | Expected Output            | Implemented?       |
|-----------------------------------------------|----------------------|-----------------------------------------------|----------------------------|--------------------|
| G3<br><sub>switchTurn_White_ReturnBlack</sub> | `switchTurn()`       | New Game; WHITE to move                       | Current turn becomes BLACK | :white_check_mark: |
| G4<br><sub>switchTurn_Black_ReturnWhite</sub> | `switchTurn()`       | Call `switchTurn()` once first; BLACK to move | Current turn becomes WHITE | :white_check_mark: |

## Deferred behavior

Check detection, king-safety filtering, checkmate, winner calculation, castling, en passant, promotion, and GameMode-specific rules are not active BVA targets for this increment. They are recorded in [`../../design/future_design_scope.md`](../../design/future_design_scope.md).
