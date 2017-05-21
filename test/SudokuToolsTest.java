
import be.howest.ti.sudokuapplication.game.Sudoku;
import be.howest.ti.sudokuapplication.game.SudokuGenerator;
import be.howest.ti.sudokuapplication.ConsoleUI.consoleDisplay;
import be.howest.ti.sudokuapplication.game.SudokuSolver;
import be.howest.ti.sudokuapplication.game.SudokuTools;
import be.howest.ti.sudokuapplication.game.SudokuValidator;
import junit.framework.AssertionFailedError;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuToolsTest {

    @Test
    public void TestGiveEmptyCells() {
        int[][] unSolvedValidSudoku = new int[][]{
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };
        Sudoku s = new Sudoku(unSolvedValidSudoku, 9, 9, 3, 3);
        SudokuTools ST = new SudokuTools(s);
        assertEquals(56, ST.giveEmptyCells());

    }

    public void TestMultipleGiveEmptyCells() {
        // Test multiple Sudoku Grids with same amount of cells left out
        for (int i = 0; i < 100; i++) {
            SudokuGenerator SG = new SudokuGenerator(9, 9, 3, 3, "GiveCellsTest");
            Sudoku s = SG.generate();
            SudokuTools ST = new SudokuTools(s);
            assertEquals(50, ST.giveEmptyCells());
        }
    }

    @Test
    public void TestgetCellCoordinatesByCellNumber() {
        int[][] unSolvedValidSudoku = new int[][]{
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };

        // Test couple single cellnumbers to see if coordinates are correct
        Sudoku s = new Sudoku(unSolvedValidSudoku, 9, 9, 3, 3);
        SudokuTools ST = new SudokuTools(s);

        Assert.assertArrayEquals(ST.getCellCoordinatesByCellNumber(0), new int[]{0, 0});
        Assert.assertArrayEquals(ST.getCellCoordinatesByCellNumber(5), new int[]{0, 5});
        Assert.assertArrayEquals(ST.getCellCoordinatesByCellNumber(80), new int[]{8, 8});
    }

    @Test
    public void TestAllGetCellCoordinatesByCellNumber() {
        int[][] unSolvedValidSudoku = new int[][]{
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };

        Sudoku s = new Sudoku(unSolvedValidSudoku, 9, 9, 3, 3);
        SudokuTools ST = new SudokuTools(s);

        // Test all cellnumbers to see if coordinates are correct
        int gridRowSize = s.getGridRowSize();
        int gridColSize = s.getGridColumnSize();
        int cellNumber = 0;
        for (int i = 0; i < gridRowSize; i++) {
            for (int j = 0; j < gridColSize; j++) {
                Assert.assertArrayEquals(ST.getCellCoordinatesByCellNumber(cellNumber), new int[]{i, j});
                cellNumber++;
            }
        }
    }

    @Test
    public void TestGiveNextEmptyCellCoordinates() {
        int[][] unSolvedValidSudoku = new int[][]{
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };
        Sudoku s = new Sudoku(unSolvedValidSudoku, 9, 9, 3, 3);
        SudokuTools TS = new SudokuTools(s);
        int grid[][] = s.getGrid();
        int emptyCells = TS.giveEmptyCells();
        int[] coordinates;
        for (int i = 0; i < emptyCells; i++) {
            coordinates = TS.giveNextEmptyCellCoordinates();
            int row = coordinates[0];
            int col = coordinates[1];
            assertEquals(grid[row][col], 0);
            s.makeNewMove(row, col, 2);
        }
    }
}
