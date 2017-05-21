
import be.howest.ti.sudokuapplication.ArrayUtils.ArrayUtils;
import be.howest.ti.sudokuapplication.ConsoleUI.consoleDisplay;
import be.howest.ti.sudokuapplication.game.SudokuValidator;
import be.howest.ti.sudokuapplication.game.Sudoku;
import be.howest.ti.sudokuapplication.game.SudokuGenerator;
import java.util.ArrayList;
import java.util.Random;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuValidatorTest {

    public SudokuValidatorTest() {
    }

    int[][] unsolvedValidSudoku1 = new int[][]{
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

    int[][] emptySudoku = new int[][]{
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    int[][] solvedSudoku1 = new int[][]{
        {8, 4, 5, 6, 3, 2, 1, 7, 9,},
        {7, 3, 2, 9, 1, 8, 6, 5, 4,},
        {1, 9, 6, 7, 4, 5, 3, 2, 8,},
        {6, 8, 3, 5, 7, 4, 9, 1, 2,},
        {4, 5, 7, 2, 9, 1, 8, 3, 6,},
        {2, 1, 9, 8, 6, 3, 5, 4, 7,},
        {3, 6, 1, 4, 2, 9, 7, 8, 5,},
        {5, 7, 4, 1, 8, 6, 2, 9, 3,},
        {9, 2, 8, 3, 5, 7, 4, 6, 1,}
    };
    int[][] invalidSudoku1 = new int[][]{
        {8, 4, 6, 6, 3, 1, 2, 7, 9,},
        {7, 3, 2, 9, 1, 8, 6, 5, 4,},
        {1, 9, 4, 7, 4, 5, 3, 2, 8,},
        {6, 8, 3, 5, 7, 4, 9, 1, 2,},
        {4, 5, 7, 2, 9, 1, 8, 3, 6,},
        {2, 1, 9, 8, 6, 3, 5, 4, 5,},
        {3, 6, 1, 4, 2, 9, 7, 8, 7,},
        {5, 7, 5, 1, 8, 6, 2, 9, 3,},
        {8, 2, 9, 3, 5, 7, 4, 6, 1,}
    };

    int[][] InvalidSolvedSudoku2 = new int[][]{
        {8, 4, 5, 6, 2, 2, 1, 7, 9,},
        {7, 6, 2, 9, 1, 8, 6, 5, 4,},
        {1, 9, 6, 7, 4, 5, 3, 2, 8,},
        {6, 8, 3, 5, 7, 4, 9, 1, 2,},
        {4, 5, 7, 2, 9, 1, 8, 2, 6,},
        {2, 1, 9, 8, 6, 3, 5, 4, 7,},
        {3, 6, 1, 4, 2, 9, 7, 8, 5,},
        {5, 3, 4, 1, 8, 6, 2, 2, 3,},
        {9, 2, 8, 3, 5, 7, 4, 6, 1,}
    };

    @Test
    public void TestIsSingleRowValid() {

        int[][] unsolvedSudokuWithInValidRow = new int[][]{
            {2, 9, 9, 0, 0, 0, 4, 0, 0}, // Duplicate 9 at beginning of row
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };
        Sudoku sudoku = new Sudoku(unsolvedSudokuWithInValidRow, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());

        unsolvedSudokuWithInValidRow = new int[][]{
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 8}, // Duplicate 1 at end of row
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };
        sudoku = new Sudoku(unsolvedSudokuWithInValidRow, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());

        int[][] invalidSolvedSudoku = new int[][]{
            {8, 4, 5, 6, 3, 2, 1, 7, 9,},
            {7, 3, 2, 9, 1, 8, 6, 5, 4,},
            {1, 9, 6, 7, 4, 5, 3, 2, 8,},
            {6, 8, 3, 5, 7, 4, 9, 1, 2,},
            {4, 5, 7, 2, 9, 1, 8, 3, 6,},
            {2, 1, 9, 8, 6, 3, 5, 4, 7,},
            {3, 6, 1, 4, 2, 9, 7, 8, 5,},
            {5, 7, 4, 1, 8, 6, 6, 9, 3,}, // Duplicate 6 in solved sudoku grid
            {9, 2, 8, 3, 5, 7, 4, 6, 1,}
        };
        sudoku = new Sudoku(invalidSolvedSudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());
    }

    @Test
    public void TestIsMultipleRandomRowValid() {
        Sudoku s = new Sudoku(9, 9, 3, 3);
        Random r = new Random();
        int gridRowSize = s.getGridRowSize();
        int gridColSize = s.getGridColumnSize();
        ArrayList<Integer> randomArray;
        for (int i = 0; i < 1000; i++) {
            s = new Sudoku(9, 9, 3, 3);
            SudokuValidator SV = new SudokuValidator(s);
            randomArray = ArrayUtils.getListOfUniqueRandomNumbersWithMax(gridRowSize);
            int randomRow = r.nextInt(gridRowSize);
            for (int col = 0; col < gridColSize; col++) {
                s.makeNewMove(randomRow, col, randomArray.get(0));
                randomArray.remove(0);
            }
            assertTrue(SV.isValid());
        }
    }

    @Test
    public void TestIsMultipleRandomColValid() {
        Sudoku s = new Sudoku(9, 9, 3, 3);
        Random r = new Random();
        int gridRowSize = s.getGridRowSize();
        int gridColSize = s.getGridColumnSize();
        ArrayList<Integer> randomArray;
        for (int i = 0; i < 1000; i++) {
            s = new Sudoku(9, 9, 3, 3);
            SudokuValidator SV = new SudokuValidator(s);
            randomArray = ArrayUtils.getListOfUniqueRandomNumbersWithMax(gridColSize);
            int randomCol = r.nextInt(gridColSize);
            for (int row = 0; row < gridRowSize; row++) {
                s.makeNewMove(row, randomCol, randomArray.get(0));
                randomArray.remove(0);
            }
            assertTrue(SV.isValid());
        }
    }

    @Test
    public void TestIsMultipleRowValid() {
        int[][] unsolvedSudokuWithInValidRow = new int[][]{
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 1},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 5, 8, 0, 0, 0, 5, 0, 6}, // duplicates 5 in row
            {0, 0, 0, 0, 2, 0, 0, 2, 0}, // duplicates 2 in row
            {0, 2, 0, 0, 6, 0, 0, 0, 6},
            {8, 0, 3, 0, 0, 5, 0, 0, 5},
            {0, 0, 0, 9, 0, 8, 0, 0, 4},
            {0, 4, 0, 4, 0, 0, 0, 0, 1} //  duplicates 4 in row
        };
        Sudoku sudoku = new Sudoku(unsolvedSudokuWithInValidRow, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());

        int[][] invalidSolvedSudoku = new int[][]{
            {8, 4, 5, 6, 3, 2, 1, 7, 9,},
            {7, 3, 2, 9, 1, 8, 6, 5, 4,},
            {1, 9, 6, 7, 5, 5, 3, 2, 8,}, // duplicate 5 in row

            {6, 8, 3, 5, 7, 4, 9, 1, 2,},
            {4, 5, 7, 2, 9, 1, 8, 3, 6,},
            {2, 1, 9, 8, 6, 6, 5, 4, 7,}, // duplicate 6 in row

            {3, 6, 1, 4, 2, 9, 7, 8, 5,},
            {5, 7, 4, 1, 8, 6, 2, 3, 3,}, // duplicate 3 in row
            {9, 2, 8, 3, 5, 7, 4, 6, 1,}
        };

        sudoku = new Sudoku(invalidSolvedSudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());
    }

    @Test
    public void TestIsValidSingleColumn() {
        int[][] unsolvedSudokuWithInvalidCol = new int[][]{
            // Duplicate 4 in column 1
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 4, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };
        Sudoku sudoku = new Sudoku(unsolvedSudokuWithInvalidCol, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());

        //Duplicate 2 in column 8
        int[][] invalidSolvedSudoku = new int[][]{
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 2},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };

        sudoku = new Sudoku(invalidSolvedSudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());
    }

    @Test
    public void TestIsValidMultipleColumns() {
        int[][] unsolvedSudokuWithInvalidCol = new int[][]{
            // Duplicate 4 in column 1
            // Duplicate 1 in column 7
            // Duplicate 5 in column 2
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 1, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 5, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };
        Sudoku sudoku = new Sudoku(unsolvedSudokuWithInvalidCol, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());

        int[][] invalidSolvedSudoku = new int[][]{
            // Duplicate 4 in column 4 
            // Duplicate 8 in column 5
            // Duplicate 9 in column 6
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 8, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 4, 5, 9, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };

        sudoku = new Sudoku(invalidSolvedSudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());
    }

    @Test
    public void TestIsValidSingleSmallSquare() {
        int[][] unsolvedSudokuWithInvalidCol = new int[][]{
            // Duplicate 5 in first small square
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {5, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };
        Sudoku sudoku = new Sudoku(unsolvedSudokuWithInvalidCol, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());

        //Duplicate 4 in last square
        int[][] invalidSolvedSudoku = new int[][]{
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 4},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };

        sudoku = new Sudoku(invalidSolvedSudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());
    }

    @Test
    public void TestIsValidMultipleSmallSquare() {
        int[][] unsolvedSudokuWithInvalidCol = new int[][]{
            // Duplicate 9 in first small square
            // Duplicate 2 in middle small square
            // Duplicate 4 in last small square
            {2, 0, 9, 0, 0, 0, 4, 0, 0},
            {9, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 2, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 0, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 4},
            {0, 0, 0, 9, 0, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };
        Sudoku sudoku = new Sudoku(unsolvedSudokuWithInvalidCol, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());

        // Duplicate 8 in second square
        // Duplicate 5 in 8th square
        // Duplicate 6 in 6th square
        int[][] invalidSolvedSudoku = new int[][]{
            {2, 0, 9, 0, 0, 8, 4, 0, 0},
            {0, 0, 5, 0, 8, 0, 6, 1, 0},
            {0, 0, 0, 0, 4, 6, 9, 0, 2},
            {0, 0, 8, 0, 0, 0, 5, 0, 6},
            {0, 0, 0, 0, 2, 0, 0, 6, 0},
            {0, 2, 0, 0, 6, 0, 0, 0, 0},
            {8, 0, 3, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 9, 5, 8, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 1}
        };

        sudoku = new Sudoku(invalidSolvedSudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());
    }

    @Test
    public void TestIsValid() {
        Sudoku sudoku = new Sudoku(solvedSudoku1, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);
        assertTrue(SV.isValid());

        sudoku = new Sudoku(emptySudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);
        assertTrue(SV.isValid());

        sudoku = new Sudoku(InvalidSolvedSudoku2, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);
        assertFalse(SV.isValid());

    }

    @Test
    public void TestSingleIsValidMove() {
        int[][] unsolvedValidSudoku = new int[][]{
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

        Sudoku sudoku = new Sudoku(unsolvedValidSudoku, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);
        boolean condition = SV.isValidMove(0, 1, 3);
        sudoku.makeNewMove(0, 1, 3);
        assertEquals(condition, SV.isValid());

        boolean condition2 = SV.isValidMove(1, 3, 8);
        sudoku.makeNewMove(1, 3, 8);
        assertEquals(condition2, SV.isValid());

        //////////////////////////////////////////////////////////
    }

    @Test
    public void test500xIsValidMove() {
        int[][] unsolvedValidSudoku = new int[][]{
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
        Sudoku sudoku = new Sudoku(unsolvedValidSudoku, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);

        // Only input new number if the move is actually valid
        // to test if isvalidmove works it will check if the sudoku 
        // is valid after 500 random (possible) inputs
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            int randomRow = r.nextInt(sudoku.getMaxValidValue());
            int randomCol = r.nextInt(sudoku.getMaxValidValue());
            int randomVal = r.nextInt(sudoku.getMaxValidValue()) + 1;

            boolean isValidMove = SV.isValidMove(randomRow, randomCol, randomVal);
            if (isValidMove) {
                sudoku.makeNewMove(randomRow, randomCol, randomVal);
            }
            assertTrue(SV.isValid());
        }

    }

    @Test
    public void TestIsSolved() {
        // Solved Valid Sudoku
        int[][] solvedSudoku = new int[][]{
            {8, 4, 5, 6, 3, 2, 1, 7, 9,},
            {7, 3, 2, 9, 1, 8, 6, 5, 4,},
            {1, 9, 6, 7, 4, 5, 3, 2, 8,},
            {6, 8, 3, 5, 7, 4, 9, 1, 2,},
            {4, 5, 7, 2, 9, 1, 8, 3, 6,},
            {2, 1, 9, 8, 6, 3, 5, 4, 7,},
            {3, 6, 1, 4, 2, 9, 7, 8, 5,},
            {5, 7, 4, 1, 8, 6, 2, 9, 3,},
            {9, 2, 8, 3, 5, 7, 4, 6, 1,}
        };

        Sudoku sudoku = new Sudoku(solvedSudoku, 9, 9, 3, 3);
        SudokuValidator SV = new SudokuValidator(sudoku);

        assertTrue(SV.isSolved());

        // Unsolved valid sudoku
        int[][] unSolvedSudoku = new int[][]{
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

        sudoku = new Sudoku(unSolvedSudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);

        assertFalse(SV.isSolved());

        // Invalid filled Sudoku
        int[][] solvedInvalidSudoku = new int[][]{
            {8, 4, 5, 6, 2, 2, 1, 7, 9,},
            {7, 6, 2, 9, 1, 8, 6, 6, 4,},
            {1, 6, 6, 7, 4, 5, 3, 2, 8,},
            {6, 8, 3, 5, 7, 4, 6, 1, 2,},
            {4, 5, 7, 2, 9, 1, 8, 2, 6,},
            {2, 1, 9, 8, 6, 3, 5, 4, 7,},
            {3, 6, 5, 4, 2, 9, 7, 8, 5,},
            {5, 3, 4, 1, 8, 6, 2, 7, 3,},
            {9, 2, 8, 3, 2, 7, 4, 6, 1,}
        };

        sudoku = new Sudoku(solvedInvalidSudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);

        assertFalse(SV.isSolved());

        // Valid partially filled Sudoku
        int[][] partiallySolvedValidSudoku = new int[][]{
            {2, 6, 9, 7, 3, 0, 4, 0, 0,},
            {4, 3, 5, 2, 8, 0, 6, 1, 0,},
            {7, 8, 1, 5, 4, 6, 9, 0, 2,},
            {9, 1, 8, 4, 7, 0, 5, 0, 6,},
            {6, 5, 7, 8, 2, 0, 0, 0, 0,},
            {3, 2, 4, 1, 6, 0, 0, 0, 0,},
            {8, 9, 3, 6, 1, 5, 0, 0, 0,},
            {1, 7, 6, 9, 0, 8, 0, 4, 0,},
            {5, 4, 2, 3, 0, 0, 0, 0, 1,}
        };

        sudoku = new Sudoku(partiallySolvedValidSudoku, 9, 9, 3, 3);
        SV = new SudokuValidator(sudoku);

        assertFalse(SV.isSolved());

    }

    @Test
    public void TestIsMultipleIsSolved() {
        SudokuGenerator SG = new SudokuGenerator(9, 9, 3, 3, "Normal");
        SudokuValidator SV;
        for (int i = 0; i < 500; i++) {
            SG.generateSudokuSecret();
            SV = new SudokuValidator(SG.getSudoku());
            assertTrue(SV.isSolved());
        }
    }
}
