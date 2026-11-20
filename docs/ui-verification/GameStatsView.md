# GameStatsView UI Verification Plan

`GameStatsView` displays the player names and identifies whose turn it is.

## Current-turn indicator requirements

- A new game displays Player 1 as the current player because WHITE moves first.
- When the current turn changes to BLACK, the indicator displays Player 2.
- After a completed move, the indicator refreshes automatically.
- The current-turn indicator appears prominently in the upper portion of the
  game view.
- The indicator should be visually separated from the existing player-name
  text and placed toward the right side to avoid overlap.
- The indicator should use a larger font than the ordinary player-name labels.

## Verification cases

| ID  | Scenario                                           | Expected result                                                                                                     | Status                         |
|-----|----------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|--------------------------------|
| GS1 | Create the view for a new game                     | Indicator displays Player 1                                                                                         | :white_check_mark:             |
| GS2 | Change the Game turn to BLACK and refresh the view | Indicator displays Player 2                                                                                         | :white_check_mark:             |
| GS3 | Complete a real move through the board UI          | Indicator updates automatically to the next player                                                                  | Pending                        |
| GS4 | Display the game header                            | Current-turn indicator is prominent, larger, and positioned toward the right without overlapping player information | Pending manual UI verification |