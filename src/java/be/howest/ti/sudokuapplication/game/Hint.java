/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.sudokuapplication.game;

/**
 *
 * @author bgregoir
 */
public class Hint {

    private final Sudoku sudoku;

    public Hint(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    public int[] getValidValuesOfCell(int cellNumber) {
        int gridSize = sudoku.getGridRowSize();
        int maxValue = sudoku.getMaxValidValue();
        int[] possibleValues = new int[gridSize + 1];
        SudokuTools ST = new SudokuTools(sudoku);
        int[] coordinates = ST.getCellCoordinatesByCellNumber(cellNumber);
        int row = coordinates[0];
        int column = coordinates[1];

        for (int value = 1; value <= maxValue; value++) {
            SudokuValidator SudokuValidator = new SudokuValidator(sudoku);
            if (sudoku.isChangableCell(row, column) && SudokuValidator.isValidMove(row, column, value)) {
                possibleValues[value] = value;
            }
        }
        return possibleValues;
    }

    public int[][] getValidValuesOfGrid() {
        int[][] grid = sudoku.getGrid();
        int maxValue = sudoku.getMaxValidValue();
        int gridLength = grid[0].length;
        int gridHeight = grid.length;
        int[][] possibleValues = new int[gridHeight * gridLength][maxValue + 1];

        int cell = 0;
        for (int row = 0; row < gridLength; row++) {
            for (int column = 0; column < gridHeight; column++, cell++) {
                for (int value = 1; value <= maxValue; value++) {
                    SudokuValidator SudokuValidator = new SudokuValidator(sudoku);
                    if (sudoku.isChangableCell(row, column) && SudokuValidator.isValidMove(row, column, value)) {
                        possibleValues[cell][value] = value;
                    }
                }
            }
        }

        return possibleValues;

    }

    public int[] giveNextCellSolution() {
        SudokuTools ST = new SudokuTools(sudoku);
        int[] nextErrorCoordinates = findNextError();
        int nextErrorRowCoordinate = nextErrorCoordinates[0];
        int nextErrorColCoordinate = nextErrorCoordinates[1];

        if (nextErrorRowCoordinate != -1 || nextErrorColCoordinate != -1) {
            int solutionForCoordinates = sudoku.getSolution(nextErrorRowCoordinate, nextErrorColCoordinate);
            return new int[]{nextErrorRowCoordinate, nextErrorColCoordinate, solutionForCoordinates};
        } else {
            int[] nextEmptyCellCoordinates = ST.giveNextEmptyCellCoordinates();
            int nextEmptyCellRowCoordinate = nextEmptyCellCoordinates[0];
            int nextEmptyCellColCoordinate = nextEmptyCellCoordinates[1];
            int solutionForCoordinates = 
                    (nextEmptyCellRowCoordinate != -1 || nextEmptyCellColCoordinate != -1) 
                    ? sudoku.getSolution(nextEmptyCellRowCoordinate, nextEmptyCellColCoordinate) : -1;
            return new int[]{nextEmptyCellRowCoordinate, nextEmptyCellColCoordinate, solutionForCoordinates};
        }
    }

    public int[] findNextError() {
        int[][] grid = sudoku.getGrid();
        int gridRowSize = sudoku.getGridRowSize();
        int gridColSize = sudoku.getGridColumnSize();
        SudokuValidator SV = new SudokuValidator(sudoku);

        for (int row = 0; row < gridRowSize; row++) {
            for (int column = 0; column < gridColSize; column++) {
                if (sudoku.isChangableCell(row, column) && grid[row][column] != 0) {
                    int temp = grid[row][column];
                    sudoku.makeNewMove(row, column, 0);
                    if (temp != sudoku.getSolution(row, column) && !SV.isValidMove(row, column, temp)) {
                        sudoku.makeNewMove(row, column, temp);
                        return new int[]{row, column};
                    }
                    sudoku.makeNewMove(row, column, temp);
                }
            }
        }
        return new int[]{-1, -1};
    }
}
