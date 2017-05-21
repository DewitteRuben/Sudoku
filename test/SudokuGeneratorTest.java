
import be.howest.ti.sudokuapplication.game.Sudoku;
import be.howest.ti.sudokuapplication.game.SudokuGenerator;
import be.howest.ti.sudokuapplication.ConsoleUI.consoleDisplay;
import be.howest.ti.sudokuapplication.game.SudokuSolver;
import be.howest.ti.sudokuapplication.game.SudokuValidator;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuGeneratorTest {

    int[][] unsolvedSudoku1 = new int[][]{
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
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

    int[][] falseSudoku1 = new int[][]{
        {8, 4, 5, 6, 3, 8, 1, 7, 9,},
        {7, 3, 2, 9, 1, 2, 6, 5, 4,},
        {1, 9, 6, 7, 4, 5, 3, 2, 8,},
        {6, 8, 3, 5, 7, 4, 9, 1, 2,},
        {4, 5, 7, 2, 9, 1, 8, 3, 6,},
        {2, 1, 9, 8, 6, 3, 5, 4, 7,},
        {3, 6, 1, 4, 2, 9, 7, 8, 5,},
        {5, 7, 4, 1, 8, 6, 2, 9, 3,},
        {9, 2, 8, 3, 5, 7, 4, 6, 1,}
    };

    int[][] falseSudoku2 = new int[][]{
        {8, 4, 5, 6, 3, 2, 1, 7, 9,},
        {8, 3, 2, 9, 1, 7, 6, 5, 4,},
        {1, 9, 6, 7, 4, 5, 3, 2, 8,},
        {6, 8, 3, 5, 7, 4, 9, 1, 2,},
        {4, 5, 7, 2, 9, 1, 8, 3, 6,},
        {2, 1, 9, 8, 6, 3, 5, 4, 7,},
        {3, 6, 1, 4, 2, 9, 7, 8, 5,},
        {5, 7, 4, 1, 8, 6, 2, 9, 3,},
        {9, 2, 8, 3, 5, 7, 4, 6, 1,}
    };

    int[][] solved3x2 = {
        {6, 1, 2, 3, 4, 5,},
        {5, 3, 4, 6, 2, 1,},
        {1, 2, 6, 5, 3, 4,},
        {4, 5, 3, 2, 1, 6,},
        {2, 4, 5, 1, 6, 3,},
        {3, 6, 1, 4, 5, 2,}
    };

    int[][] unsolved3x2 = {
        {0, 1, 2, 0, 0, 0,},
        {0, 0, 0, 6, 0, 1,},
        {0, 0, 0, 5, 3, 0,},
        {4, 5, 0, 2, 1, 0,},
        {2, 4, 0, 0, 6, 3},
        {0, 0, 0, 0, 5, 2}
    };

    // Generate 5000 sudoku secrets and check if they're valid
    @Test
    public void TestSudokuSecretGenerator() {
        SudokuGenerator SG = new SudokuGenerator(9, 9, 3, 3, "Normal");
        SudokuValidator SV;
        for (int i = 0; i < 5000; i++) {
            SG.generateSudokuSecret();
            SV = new SudokuValidator(SG.getSudoku());
            assertTrue(SV.isValid());
        }
    }

    // Generate 200 empty 12X12 (3X4) (Easy) Sudoku Grids
    @Test
    public void TestSudokuGenerator12X123X4Easy() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 200; i++) {
            SG = new SudokuGenerator(12, 12, 4, 3, "Easy");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

    // Generate 50 empty 12X12 (3X4) (Hard) Sudoku Grids
    @Test
    public void TestSudokuGenerator12X123X4Hard() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 100; i++) {
            SG = new SudokuGenerator(12, 12, 4, 3, "Hard");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

    // Generate 100 empty 12X12 (3X4) (Normal) Sudoku Grids
    @Test
    public void TestSudokuGenerator12X123X4Normal() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 100; i++) {
            SG = new SudokuGenerator(12, 12, 4, 3, "Normal");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

    // Generate 200 empty 12X12 (4X3) (Easy) Sudoku Grids
    @Test
    public void TestSudokuGenerator12X124X3Easy() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 200; i++) {
            SG = new SudokuGenerator(12, 12, 4, 3, "Easy");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));;
        }
    }

    // Generate 100 empty 12X12 (4X3) (Hard) Sudoku Grids
    @Test
    public void TestSudokuGenerator12X124X3Hard() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 100; i++) {
            SG = new SudokuGenerator(12, 12, 4, 3, "Hard");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

    // Generate 100 empty 12X12 (4X3) (Normal) Sudoku Grids
    @Test
    public void TestSudokuGenerator12X124X3Normal() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 100; i++) {
            SG = new SudokuGenerator(12, 12, 4, 3, "Normal");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

    // Generate 200 empty 9X9 (Easy) Sudoku Grids
    @Test
    public void TestSudokuGenerator9X9Easy() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 200; i++) {
            SG = new SudokuGenerator(9, 9, 3, 3, "Easy");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

    // Generate 100 empty 9X9 (Hard) Sudoku Grids
    @Test
    public void TestSudokuGenerator9X9Hard() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 100; i++) {
            SG = new SudokuGenerator(9, 9, 3, 3, "Hard");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

    // Generate 100 empty 9X9 (Normal) Sudoku Grids
    @Test
    public void TestSudokuGenerator9X9Normal() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 100; i++) {
            SG = new SudokuGenerator(9, 9, 3, 3, "Normal");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));

        }
    }

    // Generate 500 empty 6X6 (3X2) Sudoku Grids
    @Test
    public void TestSudokuGenerator6X63X2() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 500; i++) {
            SG = new SudokuGenerator(6, 6, 3, 2, "Normal");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

    // Generate 500 empty 6X6 (2X3) Sudoku Grids
    @Test
    public void TestSudokuGenerator6X62X3() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 500; i++) {
            SG = new SudokuGenerator(6, 6, 2, 3, "Normal");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

    // Generate 5000 empty 4X4 Sudoku Grids
    @Test
    public void TestSudokuGenerator4X4() {
        SudokuGenerator SG;
        SudokuValidator SV;
        for (int i = 0; i < 5000; i++) {
            SG = new SudokuGenerator(4, 4, 2, 2, "Normal");
            SV = new SudokuValidator(SG.generate());
            SudokuSolver SS = new SudokuSolver(SG.getSudoku());
            assertTrue(SV.isValid());
            assertEquals(1, SS.solve(false));
        }
    }

}
