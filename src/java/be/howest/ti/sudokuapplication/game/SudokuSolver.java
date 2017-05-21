package be.howest.ti.sudokuapplication.game;

public class SudokuSolver {

    private final Sudoku sudoku;
    private final SudokuValidator SudokuValidator;
    private int stackSize = 0;

    public SudokuSolver(Sudoku sudoku) {
        this.sudoku = sudoku;
        this.SudokuValidator = new SudokuValidator(sudoku);
    }

    public boolean fillSolve() {
        fillSolveFrom();
        return SudokuValidator.isValid();
    }

    private boolean fillSolveFrom() {
        int[][] grid = sudoku.getGrid();
        int maxValue = sudoku.getMaxValidValue();

        stackSize++;

        if (stackSize > 10000) {
            stackSize = 0;
            return false;
        }

        SudokuTools ST = new SudokuTools(sudoku);
        int[] nextEmptyCell = ST.giveNextEmptyCellCoordinates();

        int nextEmptyRowCord = nextEmptyCell[0];
        int nextEmptyColCord = nextEmptyCell[1];

        if (nextEmptyRowCord == -1) {
            return true;
        }

        for (int i = 1; i <= maxValue; i++) {
            if (SudokuValidator.isValidMove(nextEmptyRowCord, nextEmptyColCord, i)) {
                grid[nextEmptyRowCord][nextEmptyColCord] = i;

                if (fillSolveFrom()) {
                    return true;
                }

                grid[nextEmptyRowCord][nextEmptyColCord] = 0;
            }
        }

        return false;
    }

    public int solve(boolean saveSolution) {
        return solveFrom(saveSolution, 0, 0, 0);
    }

    private int solveFrom(boolean saveSolution, int i, int j, int count) {
        int[][] gridCopy = sudoku.getGrid();
        int maxValue = sudoku.getMaxValidValue();

        // Sets first found solution
        if (saveSolution && sudoku.getSolutionGrid() == null && SudokuValidator.isSolved()) {
            sudoku.setSolution(gridCopy);
        }

        if (i == gridCopy.length) {
            i = 0;
            j++;
            // we finished the first iteration of rows, now we reset i and increase j and check
            // if j is equal to the maxlength, if so we have found 1 solution (we finished 1
            // fillSolve sequence)
            if (j == gridCopy[0].length) {
                return 1 + count;
            }
        }
        // if the position i j does not equal 0 increase i by one
        if (gridCopy[i][j] != 0) {
            return solveFrom(saveSolution, i + 1, j, count);
        }

        for (int val = 1; val <= maxValue && count < 2; ++val) {
            if (SudokuValidator.isValidMove(i, j, val)) {
                gridCopy[i][j] = val;
                count = solveFrom(saveSolution, i + 1, j, count);
            }
        }

        gridCopy[i][j] = 0;
        return count;
    }
}
