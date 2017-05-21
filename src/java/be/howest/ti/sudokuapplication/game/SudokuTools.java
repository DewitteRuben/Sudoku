package be.howest.ti.sudokuapplication.game;

public class SudokuTools {

    private final Sudoku sudoku;

    public SudokuTools(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     *
     * @return Amount of empty cells
     */
    public int giveEmptyCells() {
        int[][] grid = sudoku.getGrid();
        int gridRowSize = sudoku.getGridRowSize();
        int gridColumnSize = sudoku.getGridColumnSize();
        int amount = 0;
        for (int row = 0; row < gridRowSize; row++) {
            for (int col = 0; col < gridColumnSize; col++) {
                if (grid[row][col] == 0) {
                    amount++;
                }
            }
        }
        return amount;
    }

    /**
     *
     * @param cellNumber Number of the Sudoku cell (starting from 0)
     * @return Integer array with row and column coordinates respectively
     */
    public int[] getCellCoordinatesByCellNumber(int cellNumber) {
        int[][] grid = sudoku.getGrid();
        int currentCell = 0;
        int columnLength = grid.length;
        int rowLength = grid[0].length;
        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < columnLength; col++) {
                if (currentCell == cellNumber) {
                    return new int[]{row, col};
                } else {
                    currentCell++;
                }
            }
        }
        return null;
    }

    public int[] giveNextEmptyCellCoordinates() {
        int[][] grid = sudoku.getGrid();
        int columnLength = grid.length;
        int rowLength = grid[0].length;
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                if (grid[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }
}
