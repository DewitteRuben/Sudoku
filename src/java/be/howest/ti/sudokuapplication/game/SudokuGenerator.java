package be.howest.ti.sudokuapplication.game;

import be.howest.ti.sudokuapplication.ArrayUtils.ArrayUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author wolke
 */
public class SudokuGenerator {

    private final Sudoku sudoku;
    private final Difficulty difficulty;

    public SudokuGenerator(int gridRowSize, int gridColumnSize, int boxRowSize, int boxColumnSize, String difficulty) {
        //create new empty sudoku
        this.sudoku = new Sudoku(gridRowSize, gridColumnSize, boxRowSize, boxColumnSize);
        this.difficulty = new Difficulty(gridRowSize, difficulty);
    }
    
    public Sudoku getSudoku() {
        return sudoku;
    }

    public Sudoku generate() {
        // Create a full random sudoku board
        generateSudokuSecret();

        //Solution is now the filled board
        sudoku.setSolution(sudoku.getGrid());

        // Randomly remove values from full grid
        // If removing fails restart generation process
        if (!(removeValuesRandomly())) {
            generate();
        }

        sudoku.fillChangableCells();

        return sudoku;
    }

    private boolean removeValuesRandomly() {
        int[][] grid = sudoku.getGrid();
        int gridSize = sudoku.getGridRowSize();
        int removedCells = 0;
        int i = 0;
        int cellsToRemove = difficulty.getAmountOfCellsToRemove();
        int amountForDebugging = 2;
        ArrayList<Integer> randomCells = ArrayUtils.getListOfUniqueRandomNumbersWithMax(gridSize * gridSize);

        while (removedCells != cellsToRemove) {

            if (i == (gridSize * gridSize) + 10) {
                return false;
            }

            int randomCell = (randomCells.get(i)) - 1;
            SudokuTools ST = new SudokuTools(sudoku);
            int[] cellCoordinates = ST.getCellCoordinatesByCellNumber(randomCell);
            int tempCell = grid[cellCoordinates[0]][cellCoordinates[1]];

            grid[cellCoordinates[0]][cellCoordinates[1]] = 0;

            SudokuSolver SudokuSolver = new SudokuSolver(sudoku);
            if (SudokuSolver.solve(false) == 1) {
                i++;
                removedCells++;
            } else {
                grid[cellCoordinates[0]][cellCoordinates[1]] = tempCell;
                randomCells.add(randomCell + 1);
                i++;
            }
        }
        return true;
    }

   

    private void fillBoxArea(int startRowCord, int endRowCord, int startColCord, int endColCord) {
        int[][] grid = sudoku.getGrid();
        int maxValue = sudoku.getMaxValidValue();
        ArrayList<Integer> uniqueRandomNumbers = ArrayUtils.getListOfUniqueRandomNumbersWithMax(maxValue);
        for (int row = startRowCord; row < endRowCord; row++) {
            for (int column = startColCord, i = 0; column < endColCord; column++) {
                grid[row][column] = uniqueRandomNumbers.get(i);
                uniqueRandomNumbers.remove(i);
            }
        }
    }

    private void fillBRBox() {

        int bottomRightCellRowColumnCord = sudoku.getGridRowSize() - 1;

        int BOX_WIDTH = sudoku.getBoxColumnSize();
        int BOX_HEIGHT = sudoku.getBoxRowSize();

        int squareCordOfRow = (bottomRightCellRowColumnCord / BOX_HEIGHT) * BOX_HEIGHT;
        int squareCordOfCol = (bottomRightCellRowColumnCord / BOX_WIDTH) * BOX_WIDTH;

        int endSquareCordOfRow = squareCordOfRow + BOX_HEIGHT;
        int endSquareCordOfCol = squareCordOfCol + BOX_WIDTH;

        fillBoxArea(squareCordOfRow, endSquareCordOfRow, squareCordOfCol, endSquareCordOfCol);

    }

    private void fillTLBox() {
        int endSquareCordOfRow = sudoku.getBoxRowSize();
        int endSquareCordOfCol = sudoku.getBoxColumnSize();
        fillBoxArea(0, endSquareCordOfRow, 0, endSquareCordOfCol);
    }

    private void fillCenterBox() {
        int gridSize = sudoku.getGridRowSize();
        if (gridSize >= 9) {
            int squareCordOfRow = sudoku.getBoxRowSize();
            int squareCordOfCol = sudoku.getBoxColumnSize();
            fillBoxArea(squareCordOfRow, (2 * squareCordOfRow), squareCordOfCol, (2 * squareCordOfCol));
        }
    }

    public boolean generateSudokuSecret() {

        // Make sure to start from new Sudoku grid every call
        sudoku.clearAll();

        // Start filling Sudoku boxes (top left, center (if even Sudoku), bottom right)
        // Fill top left & bottom right box
        fillTLBox();
        fillBRBox();
        // Fill center (if grid >= 9 (If there is a center))
        fillCenterBox();

        // Complete rest of Sudoku by backtrack solving
        SudokuSolver SudokuSolver = new SudokuSolver(sudoku);
        if (!(SudokuSolver.fillSolve())) {
            return generateSudokuSecret();
        }

        return true;
    }

}
