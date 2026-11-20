# BoardView UI Verification Plan

`BoardView` is a View class, so these are UI/acceptance checks rather than non-view BVA cases.

## Current implementation facts

- `BoardView` draws the board and pieces from `BoardController.getBoardSnapshot()`.
- It stores its own `selectedRow` and `selectedColumn` from the most recent in-board mouse click.
- It paints that clicked square with `SELECTED_SQUARE_COLOR`.
- It does **not** currently read `BoardController.getSelectedSquare()`.
- It does **not** currently read or draw `BoardController.getHighlightedSquares()`.
- The mouse listener calls `repaint()` before forwarding the click to `BoardController`.

Because the View and controller have separate selection state, the visible square can disagree with the actual selected square after deselection, invalid clicks, or a completed move.

## Acceptance checks for the later UI synchronization step

| ID | Scenario                                           | Expected UI result                                                  | Status             |
|----|----------------------------------------------------|---------------------------------------------------------------------|--------------------|
| V1 | Select an active-player piece                      | Only `BoardController.getSelectedSquare()` is painted as selected   | :white_check_mark: |
| V2 | Select a piece with two Game-reported destinations | Exactly those two destinations are highlighted                      | :white_check_mark: |
| V3 | Reselect another active-player piece               | Old selection/highlights disappear; new selection/highlights appear | :white_check_mark: |
| V4 | Click an unhighlighted in-board square             | Selection/highlights disappear visually                             | :white_check_mark: |
| V5 | Complete a legal move                              | Piece redraws at destination; selection/highlights disappear        | :white_check_mark: |
| V6 | Click outside the board component                  | Controller/UI state remains unchanged                               | Pending            |

Do not add a private `drawHighlightedSquare(...)` API merely to satisfy the old documentation. The implementation should be chosen during the UI step and verified through observable painting behavior or a focused rendering test.
