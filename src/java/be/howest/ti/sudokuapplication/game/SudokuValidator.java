package be.howest.ti.sudokuapplication.game;

import javax.json.JsonValue;

public class SudokuValidator {

    private final Sudoku sudoku;

    public SudokuValidator(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    private boolean hasDuplicatesIn(int[] interval) {
        int intervalLength = interval.length;
        for (int i = 1; i < intervalLength; i++) { // Starting from 1 to not count occurence of 0
            if (interval[i] > 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidRow(int row) {
        int[][] sudokuGrid = sudoku.getGrid();
        int width = sudokuGrid[0].length;

        int[] occurences = new int[width + 1];// + 1 Because an array of 9 would not cover all 9 numbers (1-9)

        for (int i = 0; i < width; i++) {
            occurences[sudokuGrid[row][i]]++;
        }

        return hasDuplicatesIn(occurences);
    }

    public boolean isValidColumn(int column) {
        int[][] sudokuGrid = sudoku.getGrid();

        int heigth = sudokuGrid.length;

        int intervalLength = heigth + 1;

        int[] occurences = new int[intervalLength];

        for (int i = 0; i < heigth; i++) {
            occurences[sudokuGrid[i][column]]++;
        }

        return hasDuplicatesIn(occurences);
    }

    public boolean isValidSquare(int row, int col) {
        int[][] sudokuGrid = sudoku.getGrid();

        int sudokuWidth = sudoku.getGridColumnSize();
        int intervalLength = sudokuWidth + 1;
        int[] occurences = new int[intervalLength];

        int BOX_WIDTH = sudoku.getBoxColumnSize();
        int BOX_HEIGHT = sudoku.getBoxRowSize();

        int squareCordOfRow = (row / BOX_HEIGHT) * BOX_HEIGHT;
        int squareCordOfCol = (col / BOX_WIDTH) * BOX_WIDTH;

        int endSquareCordOfRow = squareCordOfRow + BOX_HEIGHT;
        int endSquareCordOfCol = squareCordOfCol + BOX_WIDTH;

        for (int i = squareCordOfRow; i < endSquareCordOfRow; i++) {
            for (int j = squareCordOfCol; j < endSquareCordOfCol; j++) {
                occurences[sudokuGrid[i][j]]++;
            }
        }

        return hasDuplicatesIn(occurences);

    }

    private boolean isAllRowsValid() {
        int height = sudoku.getGridRowSize();

        for (int i = 0; i < height; i++) {
            if (!isValidRow(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllColumnsValid() {
        int width = sudoku.getGridColumnSize();

        // Check all columns
        for (int i = 0; i < width; i++) {
            if (!isValidColumn(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllSquaresValid() {
        int width = sudoku.getGridColumnSize();
        int height = sudoku.getGridRowSize();
        int sudokuSquareSize = sudoku.getBoxRowSize();

        // Check all small squares
        for (int i = 0; i < height; i += sudokuSquareSize) {
            for (int j = 0; j < width; j += sudokuSquareSize) {
                if (!isValidSquare(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidMove(int row, int col, int value) {
        if (isPossibleInput(row, col, value)) {
            if (isValueInRow(row, value) || isValueInCol(col, value) || isValueInBox(row, col, value)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPossibleInput(int row, int col, int value) {
        return isValidInput(value) && isInGridRange(row, col) && sudoku.isChangableCell(row, col);
    }

    private boolean isValidInput(int value) {
        return value <= sudoku.getMaxValidValue() && value >= 0;
    }

    private boolean isInGridRange(int row, int col) {
        return row <= sudoku.getGridRowSize() && row >= 0 && col <= sudoku.getGridColumnSize() && col >= 0;
    }

    private boolean isValueInRow(int row, int value) {
        int grid[][] = sudoku.getGrid();
        int GRID_HEIGTH = sudoku.getGridRowSize();

        if (!(row > GRID_HEIGTH)) {
            for (int column = 0; column < GRID_HEIGTH; column++) {
                if (grid[row][column] == value) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValueInCol(int column, int value) {
        int grid[][] = sudoku.getGrid();
        int GRID_WIDTH = sudoku.getGridColumnSize();

        if (!(column > GRID_WIDTH)) {
            for (int row = 0; row < GRID_WIDTH; row++) {
                if (grid[row][column] == value) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValueInBox(int row, int col, int value) {

        int grid[][] = sudoku.getGrid();
        int BOX_WIDTH = sudoku.getBoxColumnSize();
        int BOX_HEIGHT = sudoku.getBoxRowSize();

        //Coordinaten berekenen van klein vierkant uit huidige cel coordinaten
        int squareCordOfRow = (row / BOX_HEIGHT) * BOX_HEIGHT;
        int squareCordOfCol = (col / BOX_WIDTH) * BOX_WIDTH;

        //Coordinaten van einde van klein vierkant berekenen
        int endSquareCordOfRow = squareCordOfRow + BOX_HEIGHT;
        int endSquareCordOfCol = squareCordOfCol + BOX_WIDTH;

        for (int i = squareCordOfRow; i < endSquareCordOfRow; i++) {
            for (int j = squareCordOfCol; j < endSquareCordOfCol; j++) {
                if (grid[i][j] == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValid() {
        return isAllColumnsValid() && isAllRowsValid() && isAllSquaresValid();
    }

    private boolean isFilled() {
        int grid[][] = sudoku.getGrid();
        int gridRowSize = sudoku.getGridRowSize();
        int gridColumnSize = sudoku.getGridColumnSize();
        for (int row = 0; row < gridRowSize; row++) {
            for (int col = 0; col < gridColumnSize; col++) {
                if (grid[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSolved() {
        return isFilled() && isValid();
    }

}
