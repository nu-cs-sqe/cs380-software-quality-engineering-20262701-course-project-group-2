# BVA Analysis: `Location`

`Location` stores integer coordinates. It does not enforce the chess-board range; callers such as BoardController decide whether a coordinate is in `[0,7]`.

`getColumn()` and `getRow()` are simple getters and do not require separate BVA cases.

## Method under test: `equals(Object other)`

| Test Case ID                                                      | Method(s) under test | State of the System                          | Expected Output | Implemented?       |
|-------------------------------------------------------------------|----------------------|----------------------------------------------|-----------------|--------------------|
| L1<br><sub>equals_SameInstance_ReturnTrue</sub>                   | `equals(Object)`     | Compare a Location to itself                 | `true`          | :white_check_mark: |
| L2<br><sub>equals_Null_ReturnFalse</sub>                          | `equals(Object)`     | Compare a Location to `null`                 | `false`         | :white_check_mark: |
| L3<br><sub>equals_DistinctObjectsSameCoordinates_ReturnTrue</sub> | `equals(Object)`     | `(3,4)` compared with a distinct `(3,4)`     | `true`          | :X:                |
| L4<br><sub>equals_DifferentColumn_ReturnFalse</sub>               | `equals(Object)`     | `(3,4)` compared with `(2,4)`                | `false`         | :X:                |
| L5<br><sub>equals_DifferentRow_ReturnFalse</sub>                  | `equals(Object)`     | `(3,4)` compared with `(3,5)`                | `false`         | :X:                |
| L6<br><sub>equals_NonLocation_ReturnFalse</sub>                   | `equals(Object)`     | `(3,4)` compared with a String or other type | `false`         | :X:                |

## Method under test: `hashCode()`

The required contract is consistency with `equals`; the exact numeric formula is an implementation detail.

| Test Case ID                                                  | Method(s) under test | State of the System                              | Expected Output  | Implemented? |
|---------------------------------------------------------------|----------------------|--------------------------------------------------|------------------|--------------|
| L7<br><sub>hashCode_EqualLocations_ReturnEqualHashCodes</sub> | `hashCode()`         | Two distinct Locations with the same coordinates | Equal hash codes | :X:          |
