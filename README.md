[![Gradle Build](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/actions/workflows/main.yml/badge.svg)](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/actions/workflows/main.yml)

[![Missed Complexity](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/raw/badges/complexity.svg)](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/actions/workflows/quality-badges.yml)

[![Mutation Score](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/raw/badges/mutation.svg)](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/actions/workflows/quality-badges.yml)

[![Checkstyle](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/raw/badges/checkstyle.svg)](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/actions/workflows/quality-badges.yml)

[![SpotBugs](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/raw/badges/spotbugs.svg)](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-2/actions/workflows/quality-badges.yml)

# 2D CHESS GAME

## Contributors
- Annie Liu
- Ian Song
- Andres Arencibia

## Dependencies
- JDK 11
- JUnit 5.10
- Gradle 8.10

## Quality Tools
- Checkstyle
- SpotBugs Gradle Plugin 6.0.25
- JaCoCo
- PIT Gradle Plugin 1.15.0

## Exceptions

### Code Coverage
N/A

### Mutation Testing

The following mutants were reviewed and determined to be equivalent:

- `King#getValidMoves`: changing column addition to subtraction produces
  the same set because `DIRECTIONS` contains every mirrored direction.
- `King#getValidMoves`: changing row addition to subtraction produces
  the same set for the same reason.
- `Knight#getValidMoves`: changing column addition to subtraction produces
  the same set because `JUMP_OFFSETS` contains mirrored jumps.
- `Knight#getValidMoves`: changing row addition to subtraction produces
  the same set for the same reason.
- `Pawn#getValidMoves`: replacing the newly created empty set with another 
  empty set does not change the specified collection contents.

### Others
N/A

https://github.com/nu-cs-sqe