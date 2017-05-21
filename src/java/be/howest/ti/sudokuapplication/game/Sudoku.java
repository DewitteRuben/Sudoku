package be.howest.ti.sudokuapplication.game;

import be.howest.ti.sudokuapplication.ArrayUtils.ArrayUtils;

import java.util.Arrays;

public class Sudoku {

    private final int[][] grid;
    private int[][] solution;

    private final int gridColumnSize;
    private final int gridRowSize;

    private final int boxRowSize;
    private final int boxColumnSize;

    private final int maxValidValue;
    private boolean[][] mutableGrid;

    /**
     *
     * @param grid Twodimensional array containing a Sudoku
     * @param gridRowSize Row size of entire Sudoku
     * @param gridColumnSize Column size of entire Sudoku
     * @param boxRowSize Row size of Sudoku box, (amount of rows in small grid).
     * Notice: row size = 3 is not equivalent with eg. 3x2, 3 is amount of
     * actual rows groups in sudoku grid while row size is actual number of rows
     * in small box.
     * @param boxColumnSize Column size of Sudoku box, (amount of columns in
     * small grid)
     */
    public Sudoku(int[][] grid, int gridRowSize, int gridColumnSize, int boxRowSize, int boxColumnSize) {
        this.grid = ArrayUtils.twoDimensionalArrayCopy(grid);
        this.gridRowSize = gridRowSize;
        this.gridColumnSize = gridColumnSize;
        this.boxRowSize = boxRowSize;
        this.boxColumnSize = boxColumnSize;
        maxValidValue = gridRowSize;
        fillChangableCells();
        fillSolution();
    }

    /**
     *
     * @param gridRowSize Row size of entire Sudoku
     * @param gridColumnSize Column size of entire Sudoku
     * @param boxRowSize Row size of Sudoku box, (amount of rows in small grid).
     * Notice: row size = 3 is not equivalent with eg. 3x2, 3 is amount of
     * actual rows groups in sudoku grid while row size is actual number of rows
     * in small box.
     * @param boxColumnSize Column size of Sudoku box, (amount of columns in
     * small grid)
     */
    public Sudoku(int gridRowSize, int gridColumnSize, int boxRowSize, int boxColumnSize) {
        this(new int[gridRowSize][gridColumnSize], gridRowSize, gridColumnSize, boxRowSize, boxColumnSize);
    }

    /**
     * Copy constructor
     *
     * @param sudoku
     */
    public Sudoku(Sudoku sudoku) {
        grid = ArrayUtils.twoDimensionalArrayCopy(sudoku.getGrid());
        mutableGrid = ArrayUtils.twoDimensionalArrayCopy(sudoku.getMutableGrid());
        solution = sudoku.getSolutionGrid() != null ? 
        ArrayUtils.twoDimensionalArrayCopy(sudoku.getSolutionGrid()) : null;
        gridRowSize = sudoku.getGridRowSize();
        gridColumnSize = sudoku.getGridColumnSize();
        boxRowSize = sudoku.getBoxRowSize();
        boxColumnSize = sudoku.getBoxColumnSize();
        maxValidValue = gridRowSize;
    }

    public void fillSolution() {
        SudokuSolver SS = new SudokuSolver(this);
        SS.solve(true);
    }

    /**
     *
     * @return highest valid value
     */
    public int getMaxValidValue() {
        return maxValidValue;
    }

    /**
     *
     * @return Sudoku grid as twodimensional array
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     *
     * @return
     */
    public int getGridColumnSize() {
        return gridColumnSize;
    }

    public int getGridRowSize() {
        return gridRowSize;
    }

    /**
     *
     * @return
     */
    public boolean[][] getMutableGrid() {
        return mutableGrid;
    }

    /**
     *
     * @return
     */
    public int getBoxRowSize() {
        return boxRowSize;
    }

    /**
     *
     * @return
     */
    public int getBoxColumnSize() {
        return boxColumnSize;
    }

    /**
     *
     * @return
     */
    public int[][] getSolutionGrid() {
        return solution;
    }

    public int getSolution(int row, int col) {
        return solution[row][col];
    }

    public int getCellValue(int row, int col) {
        return grid[row][col];
    }

    public void setSolution(int[][] solution) {
        this.solution = ArrayUtils.twoDimensionalArrayCopy(solution);
    }

    /**
     * @param row coordinate
     * @param col coordinate
     * @return whether or not cell with specified coordinates is mutable.
     */
    public boolean isChangableCell(int row, int col) {
        return mutableGrid[row][col];
    }

    public void fillChangableCells() {
        mutableGrid = new boolean[gridRowSize][gridColumnSize];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) {
                    mutableGrid[row][col] = true;
                }
            }
        }
    }

    private void setCell(int row, int col, int value) {
        grid[row][col] = value;
    }



    /**
     *
     * @param row coordinate
     * @param col coordinate
     * @param value new value to input
     * @return whether or not the move was valid.
     */
    public boolean makeNewMove(int row, int col, int value) {
        SudokuValidator SudokuValidator = new SudokuValidator(this);
        if ((SudokuValidator.isPossibleInput(row, col, value))) {
            setCell(row, col, value);
            return true;
        }
        return false;
    }

    /**
     * Clears all mutable cells
     */
    public void clear() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (mutableGrid[row][col]) {
                    grid[row][col] = 0;
                }
            }
        }
    }

    /**
     * Clears all cells
     */
    public void clearAll() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = 0;
            }
        }
    }

    @Override
    public String toString() {
        if (grid != null) {
            String sudokuString = "";
            int columnLength = grid.length;
            int rowLength = grid[0].length;

            for (int row = 0; row < rowLength; row++) {
                for (int col = 0; col < columnLength; col++) {
                    if (grid[row][col] == 0) {
                        sudokuString += ".";
                    } else {
                        sudokuString += ("" + grid[row][col] + "");
                    }
                    sudokuString += "|";
                }
            }

            return sudokuString;
        }
        return "No Sudoku defined";
    }

    public String solutionToString() {
        if (solution != null) {
            String solutionString = "";
            for (int row = 0; row < solution.length; row++) {
                for (int col = 0; col < solution[0].length; col++) {
                    solutionString += ("" + solution[row][col] + "|");
                }
            }
            return solutionString;
        }
        return "No solution defined";
    }

}
