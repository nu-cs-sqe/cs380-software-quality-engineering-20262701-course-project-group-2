# BVA Analysis for BoardView

### Method under test: `drawHighlightedSquare(Graphics graphics, Location location)`

Inputs:
- Location(s) of the squares to be highlighted (Collection)
  - data values to test: empty set, 1 element, multiple elements, maximum possible elements
- status of the highlighted square: cases, {already highlighted, not highlighted} 

Output:
- boolean (square is drawn w/ highlights vs square is not highlighted)

#### Test cases

- **V1: drawHighlightedSquare_noHighlights_oneSquare** ( :x: )
    - **State of the system**:
      - highlighted set: `{(3, 3)}` (empty square)
    - **Expected output**:  (3,3) is highlighted

- **V2: drawHighlightedSquare_NoHighlights_emptySet** ( :x: )
    - **State of the system**:
      - highlighted set: `{}` (nothing selected)
    - **Expected output**: no change
  
- **V3: drawHighlightedSquare_noHighlights_EightKnightSquares** ( :x: )
    - **State of the system**:
      - highlighted set: the eight knight destinations from M25, `{(1, 2), (1, 4), (2, 1), (2, 5), (4, 1), (4, 5), (5, 2), (5, 4)}`
    - **Expected output**: all blocks in the highlighted set
  
- **V4: drawHighlightedSquare_noHighlights_queenTwentySevenSquaresUpperBound** ( :x: )
    - **State of the system**:
      - highlighted set: the 27 queen destinations from M24
    - **Expected output**: all 27 squares radiating from the queen highlighted
  
- **V5: drawHighlightedSquare_existingHighlights_reHighlightNewSquare** ( :x: )
    - **State of the system**:
      - first paint with the highlighted set `{(4, 5)}`
      - then the selection changes and a second paint runs with `{(4, 4)}` 
    - **Expected output**: after the second paint only (4, 4) is highlighted; (4, 5) is back to a plain light tile


- **V6: drawHighlightedSquare_existingHighlights_reHighlightSetContainingOldSet** ( :x: )
    - **State of the system**:
        - first paint with the highlighted set `{(4, 5)}`
        - then the selection changes and a second paint runs with `{(4, 4), (4,5}`
    - **Expected output**: (4,4) and (4,5) highlighted
  
