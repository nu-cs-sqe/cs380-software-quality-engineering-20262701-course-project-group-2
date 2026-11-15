# BVA Analysis: `Location`

### Method under test: `getColumn()`

**Purpose / use:** Provides the stored column coordinate to Board/Game code when indexing the board.

**Expected behavior:** Returns the column stored in this `Location`. The method does not decide whether the coordinate is inside the chess board; methods that use a `Location` are responsible for that validation.

No additional BVA test cases are applicable. `getColumn()` is a simple getter with no input or branching behavior.

#### Step 4: Planned test cases

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `getColumn()`        |                     |                 | :X:          |

### Method under test: `getRow()`

**Purpose / use:** Provides the stored row coordinate to Board/Game code when indexing the board.

**Expected behavior:** Returns the row stored in this `Location`. The method does not decide whether the coordinate is inside the chess board; methods that use a `Location` are responsible for that validation.

No additional BVA test cases are applicable. `getRow()` is a simple getter with no input or branching behavior.

#### Step 4: Planned test cases

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `getRow()`           |                     |                 | :X:          |

---

### Planned method under test: `equals(Object other)`

**Purpose / use:** Allows two `Location` objects to be compared by their coordinates instead of by object identity.

**Expected behavior:** Returns whether the other object represents the same column and row.

Inputs:

Outputs:

| Test Case ID | Method(s) under test   | State of the System | Expected Output | Implemented? |
|--------------|------------------------|---------------------|-----------------|--------------|
|              | `equals(Object other)` |                     |                 | :X:          |

---

### Planned method under test: `hashCode()`

**Purpose / use:** Allows `Location` objects to work correctly in `Set<Location>` / hash-based collections used for moves and highlights.

**Expected behavior:** Returns a hash code consistent with `equals()`.

Inputs:

Outputs:

| Test Case ID | Method(s) under test | State of the System | Expected Output | Implemented? |
|--------------|----------------------|---------------------|-----------------|--------------|
|              | `hashCode()`         |                     |                 | :X:          |
