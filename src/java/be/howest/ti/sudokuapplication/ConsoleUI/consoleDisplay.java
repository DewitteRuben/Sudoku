package be.howest.ti.sudokuapplication.ConsoleUI;
import be.howest.ti.sudokuapplication.game.Sudoku;

/**
 *
 * @author Ruben
 */
public class consoleDisplay {

    //    OM TE DEBUGGEN IN CONSOLE -WL
    public static void consolePrint(Sudoku sudoku) {
        int gridRowSize = sudoku.getGridRowSize();
        int gridColumnSize = sudoku.getGridColumnSize();
        int boxRowSize = sudoku.getBoxRowSize();
        int boxColumnSize = sudoku.getBoxColumnSize();
        int[][] grid = sudoku.getGrid();
        for (int row = 0; row < gridRowSize; row++) {
            if (row % boxRowSize == 0) {
                System.out.println();
            }
            for (int col = 0; col < gridColumnSize; col++) {
                if (col % boxColumnSize == 0) {
                    System.out.print(" ");
                }
                System.out.print(grid[row][col] + " ");

            }
            System.out.println();
        }
    }
}
