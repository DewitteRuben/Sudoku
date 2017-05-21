
import be.howest.ti.sudokuapplication.ConsoleUI.consoleDisplay;
import be.howest.ti.sudokuapplication.game.Hint;
import be.howest.ti.sudokuapplication.game.Sudoku;
import be.howest.ti.sudokuapplication.game.SudokuSolver;
import be.howest.ti.sudokuapplication.game.SudokuTools;
import be.howest.ti.sudokuapplication.game.SudokuValidator;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuHintTest {

    @Test
    public void TestFindNextError() {
        int[][] validSudoku = new int[][]{
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
        Sudoku s = new Sudoku(validSudoku, 9, 9, 3, 3);

        // Add new error
        s.makeNewMove(5, 3, 6);
        Hint h = new Hint(s);

        // Check to see if error has been found
        int[] cellCoordinates = h.findNextError();
        assertEquals(cellCoordinates[0], 5);
        assertEquals(cellCoordinates[1], 3);

        // Fix error and check to see if error can't be found anymore
        s.makeNewMove(5, 3, 0);
        cellCoordinates = h.findNextError();
        assertEquals(cellCoordinates[0], -1);
        assertEquals(cellCoordinates[1], -1);
    }

    @Test
    public void TestGiveNextCellSolutionWithError() {
        int[][] validSudoku = new int[][]{
            {3, 0, 4, 0},
            {1, 0, 0, 0},
            {0, 3, 0, 2},
            {2, 0, 3, 4},};
        Sudoku s = new Sudoku(validSudoku, 4, 4, 2, 2);
        s.makeNewMove(0, 1, 2);
        s.makeNewMove(0, 3, 1);
        s.makeNewMove(1, 1, 4);
        s.makeNewMove(1, 2, 2);
        s.makeNewMove(1, 3, 3);
        s.makeNewMove(2, 0, 4);
        s.makeNewMove(2, 2, 2);
        s.makeNewMove(3, 1, 1);
        Hint h = new Hint(s);
        int[] next = h.giveNextCellSolution();
        assertArrayEquals(next, new int[]{2, 2, 1});
    }

    @Test
    public void getValidValuesOfCell() {
        int[][] validSudoku = new int[][]{
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
        Sudoku s = new Sudoku(validSudoku, 9, 9, 3, 3);
        Hint h = new Hint(s);
        SudokuValidator SV = new SudokuValidator(s);
        SudokuTools ST = new SudokuTools(s);
        Random r = new Random();

        // Test a random cell to see if getValidValues is valid for said cell
        for (int i = 0; i < 500; i++) {
            int randomCell = r.nextInt(81);
            int[] cellCoordinates = ST.getCellCoordinatesByCellNumber(randomCell);
            int randomCellRow = cellCoordinates[0];
            int randomCellCol = cellCoordinates[1];
            int[] validValuesOfCell = h.getValidValuesOfCell(randomCell);
            for (int j = 0; j < validValuesOfCell.length; j++) {
                if (validValuesOfCell[j] != 0) {
                    assertTrue(SV.isValidMove(randomCellRow, randomCellCol, validValuesOfCell[j]));
                }
            }
        }
    }

    @Test
    public void TestGiveNextCellSolution() {
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

        int[][] solvedVersion = new int[][]{
            {2, 6, 9, 3, 5, 1, 4, 7, 8},
            {4, 7, 5, 2, 8, 9, 6, 1, 3},
            {3, 8, 1, 7, 4, 6, 9, 5, 2},
            {7, 3, 8, 1, 9, 4, 5, 2, 6},
            {9, 5, 6, 8, 2, 7, 1, 3, 4},
            {1, 2, 4, 5, 6, 3, 7, 8, 9},
            {8, 9, 3, 4, 1, 5, 2, 6, 7},
            {6, 1, 2, 9, 7, 8, 3, 4, 5},
            {5, 4, 7, 6, 3, 2, 8, 9, 1}
        };

        Sudoku unsolvedSudoku = new Sudoku(unSolvedValidSudoku, 9, 9, 3, 3);
        Sudoku solvedSudoku = new Sudoku(solvedVersion, 9, 9, 3, 3);
        SudokuTools TS = new SudokuTools(unsolvedSudoku);
        Hint h = new Hint(unsolvedSudoku);
        int emptyCells = TS.giveEmptyCells();

        int[][] solvedGrid = solvedSudoku.getGrid();

        // Compare findNextSolution result to pre-filled sudoku and apply solution for each cell
        for (int i = 0; i < emptyCells; i++) {
            int[] nextcellSolutionCoordinates = h.giveNextCellSolution();
            int[] nextEmptyCellCoordinates = TS.giveNextEmptyCellCoordinates();
            int rowEmpty = nextEmptyCellCoordinates[0];
            int colEmpty = nextEmptyCellCoordinates[1];
            int sol = nextcellSolutionCoordinates[2];

            assertEquals(solvedGrid[rowEmpty][colEmpty], sol);
            unsolvedSudoku.makeNewMove(rowEmpty, colEmpty, sol);
        }
        
        // Check if Sudoku has been solved correctly by solveNext function
        SudokuValidator SV = new SudokuValidator(unsolvedSudoku);
        assertTrue(SV.isSolved());

    }
}
