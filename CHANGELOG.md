## Release - Sudoku v0.0.1

## Board:
- Added generatePuzzle() : continuously generates a new board while this board's permutation cannot meet the specified clue values
- Changed validBoard() : returns true if Solver has one unique solution
- Added countClues() : Returns the amount of cells in this board with attribute isClue = true
- Fixed setPuzzle() : Could sometimes get stuck in an infinite loop depending on desired clue count.
- Changed setPuzzle() : returns Boolean. keeps track of which Cells are not yet tried. while this board has more clue values than desired and there are still cells to try, take a random cell from not yet tried and remove it, set random cells value to zero, if removing this value broke the uniqueness of this board restore its initial value. If after removing every possible cell there are still more than desired, return false. Otherwise, return true.

## GUIController:
- Added clue count text display
- Changed newBoard() : Rebuilds control panel responsible for holding mistakes counter and selected value
- Changed createWindow() : Force a slightly larger minimum frame size

## Solver:
- Replaced hasSolutions() with getSolutions() : returns 0 if no solutions exist, returns 1 if one unique solution exists, returns 2 if more than one solution exists.
- Changed recSolve() : solver tries to solve the board more than once instead of stopping.

## Added Interface SudokuCommons:
- Currently only contains the clueCount variable. This value will be mutatable by the user in the future via the GUI.
- The comment above the clueCount variable details the average runtime, followed by a 95% confidence interval, of the board trying to generate a new puzzle. Because generating a new puzzle means a lot of backtracking, and some permutations can't be solved with the desired clue count, low clue counts produce very high generation times. A desired clue count of 23 will take 1.7 seconds on average to generate a new puzzle, with 2 second generation times still being plausible.

## Added TestBoard:
- Tests the average creation time of a board with the current clue count. 