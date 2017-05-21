
import be.howest.ti.sudokuapplication.game.Sudoku;
import be.howest.ti.sudokuapplication.game.SudokuSolver;
import be.howest.ti.sudokuapplication.game.SudokuValidator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuSolverTest {

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

    //Hard
    int[][] inValidUnsolvedSudoku = new int[][]{
        {0, 1, 0, 0, 2, 0, 0, 7, 0},
        {0, 0, 9, 0, 8, 4, 0, 0, 6},
        {0, 0, 0, 6, 0, 0, 0, 8, 0},
        {6, 0, 0, 8, 0, 0, 1, 0, 0},
        {0, 7, 1, 0, 0, 0, 9, 6, 0},
        {0, 0, 5, 0, 0, 3, 0, 0, 2},
        {0, 9, 0, 0, 0, 8, 0, 0, 0},
        {0, 0, 0, 2, 7, 0, 5, 0, 0},
        {0, 4, 0, 0, 0, 0, 0, 1, 0}
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
        {5, 3, 0, 0, 0, 7, 0, 5, 0},
        {6, 0, 11, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, -4, 0, 2, 8, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 1, 0, 7, 9}
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

    int[][] semiSolved = {
        {6, 1, 2, 3, 4, 5,},
        {5, 3, 4, 6, 2, 1,},
        {0, 0, 0, 5, 3, 0,},
        {4, 5, 0, 2, 1, 0,},
        {2, 4, 0, 0, 6, 3},
        {0, 0, 0, 0, 5, 2}
    };

    int[][] unsolved2x3 = {
        {5, 0, 1, 0, 0, 0,},
        {0, 1, 4, 3, 0, 0,},
        {0, 0, 0, 0, 0, 0,},
        {0, 3, 0, 2, 0, 5,},
        {0, 0, 0, 0, 0, 3,},
        {1, 0, 0, 0, 0, 0,},};

    @Test
    public void testFillSolve9X9() {
        // Fill solve valid unsolved Sudoku 9x9
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
        SudokuSolver SS = new SudokuSolver(s);
        SudokuValidator SV = new SudokuValidator(s);
        SS.fillSolve();
        assertTrue(SV.isSolved());

        /// Fill solve invalid unsolved Sudoku 9x9
        int[][] inValidUnsolvedSudoku = new int[][]{
            {0, 1, 0, 0, 2, 0, 0, 7, 0},
            {0, 0, 9, 0, 8, 4, 0, 0, 6},
            {0, 0, 0, 6, 0, 0, 0, 8, 0},
            {6, 0, 0, 8, 0, 0, 1, 0, 0},
            {0, 7, 1, 0, 0, 0, 9, 6, 0},
            {0, 0, 5, 0, 0, 3, 0, 0, 2},
            {0, 9, 0, 0, 0, 8, 0, 0, 0},
            {0, 0, 0, 2, 7, 0, 5, 0, 0},
            {0, 4, 0, 0, 0, 0, 0, 1, 0}
        };
        s = new Sudoku(inValidUnsolvedSudoku, 9, 9, 3, 3);
        SS = new SudokuSolver(s);
        SV = new SudokuValidator(s);
        SS.fillSolve();
        assertFalse(SV.isSolved());

    }

    @Test
    public void testFillSolve6X6() {
        // Fill solve valid unsolved Sudoku 6x6 (2x3)
        int[][] unsolved2x3 = {
            {5, 0, 1, 0, 0, 0,},
            {0, 1, 4, 3, 0, 0,},
            {0, 0, 0, 0, 0, 0,},
            {0, 3, 0, 2, 0, 5,},
            {0, 0, 0, 0, 0, 3,},
            {1, 0, 0, 0, 0, 0,},};

        Sudoku s = new Sudoku(unsolved2x3, 6, 6, 3, 2);
        SudokuSolver SS = new SudokuSolver(s);
        SudokuValidator SV = new SudokuValidator(s);
        SS.fillSolve();
        assertTrue(SV.isSolved());

        /// Fill solve invalid unsolved Sudoku 6x6 (3x2)
        int[][] unsolved3x2 = {
            {0, 5, 0, 0, 3, 0,},
            {0, 0, 1, 0, 0, 5,},
            {0, 0, 0, 6, 0, 0,},
            {0, 0, 0, 0, 0, 2,},
            {0, 0, 2, 4, 0, 3,},
            {3, 0, 0, 0, 2, 0,},};
        s = new Sudoku(unsolved3x2, 6, 6, 3, 2);
        SS = new SudokuSolver(s);
        SV = new SudokuValidator(s);
        SS.fillSolve();
        assertFalse(SV.isSolved());

    }

    @Test
    public void TestFillSudoku12x12() {
        int[][] unSolvedValidSudoku = new int[][]{
            {7, 0, 3, 1, 2, 0, 5, 0, 8, 10, 0, 12},
            {0, 0, 10, 6, 0, 5, 0, 2, 12, 0, 9, 0},
            {0, 6, 5, 9, 0, 0, 0, 0, 0, 8, 2, 0},
            {0, 12, 2, 0, 11, 8, 3, 0, 0, 0, 0, 6},
            {2, 0, 4, 5, 8, 0, 0, 11, 7, 12, 0, 10},
            {3, 5, 0, 0, 6, 1, 9, 12, 10, 11, 4, 0},
            {6, 0, 0, 3, 12, 10, 0, 1, 4, 0, 8, 0},
            {9, 0, 12, 0, 0, 11, 8, 3, 5, 0, 0, 7},
            {8, 0, 6, 4, 0, 0, 10, 9, 3, 5, 0, 0},
            {5, 4, 9, 0, 0, 0, 11, 0, 6, 2, 7, 1},
            {0, 3, 1, 0, 9, 6, 7, 5, 0, 0, 10, 8},
            {10, 0, 7, 0, 5, 2, 12, 0, 1, 3, 6, 0},};
        Sudoku s = new Sudoku(unSolvedValidSudoku, 12, 12, 4, 3);
        SudokuSolver SS = new SudokuSolver(s);
        SudokuValidator SV = new SudokuValidator(s);
        SS.fillSolve();
        assertTrue(SV.isSolved());

    }

    @Test
    public void testPuzzleUniqueness() {
        int[][] sudokuWithTwoSolutions = new int[][]{
            {9, 0, 6, 0, 7, 0, 4, 0, 3,},
            {0, 0, 0, 4, 0, 0, 2, 0, 0,},
            {0, 7, 0, 0, 2, 3, 0, 1, 0,},
            {5, 0, 0, 0, 0, 0, 1, 0, 0,},
            {0, 4, 0, 2, 0, 8, 0, 6, 0,},
            {0, 0, 3, 0, 0, 0, 0, 0, 5,},
            {0, 3, 0, 7, 0, 0, 0, 5, 0,},
            {0, 0, 7, 0, 0, 5, 0, 0, 0,},
            {4, 0, 5, 0, 1, 0, 7, 0, 8,}
        };

        int[][] sudokuWithOneSolution4X4 = new int[][]{
            {2, 0, 0, 3},
            {0, 0, 0, 0},
            {0, 0, 1, 0},
            {0, 4, 0, 0},};

        int[][] sudokuWithOneSolution = new int[][]{
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

        int[][] SudokuWithMultipleSolutions2x3 = {
            {0, 0, 0, 0, 0, 1,},
            {0, 0, 0, 0, 6, 0,},
            {0, 0, 0, 0, 5, 6,},
            {3, 0, 0, 0, 0, 4,},
            {0, 0, 4, 5, 0, 0,},
            {0, 5, 2, 0, 0, 0,},};

        int[][] unique12x123x4 = new int[][]{
            {7, 0, 3, 1, 2, 0, 5, 0, 8, 10, 0, 12},
            {0, 0, 10, 6, 0, 5, 0, 2, 12, 0, 9, 0},
            {0, 6, 5, 9, 0, 0, 0, 0, 0, 8, 2, 0},
            {0, 12, 2, 0, 11, 8, 3, 0, 0, 0, 0, 6},
            {2, 0, 4, 5, 8, 0, 0, 11, 7, 12, 0, 10},
            {3, 5, 0, 0, 6, 1, 9, 12, 10, 11, 4, 0},
            {6, 0, 0, 3, 12, 10, 0, 1, 4, 0, 8, 0},
            {9, 0, 12, 0, 0, 11, 8, 3, 5, 0, 0, 7},
            {8, 0, 6, 4, 0, 0, 10, 9, 3, 5, 0, 0},
            {5, 4, 9, 0, 0, 0, 11, 0, 6, 2, 7, 1},
            {0, 3, 1, 0, 9, 6, 7, 5, 0, 0, 10, 8},
            {10, 0, 7, 0, 5, 2, 12, 0, 1, 3, 6, 0},};
        
        int[][] notSoUnique12x12 = new int[][]{
            {7, 0, 3, 1, 2, 0, 5, 0, 8, 10, 0, 12},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 6, 5, 9, 0, 0, 0, 0, 0, 8, 2, 0},
            {0, 0, 0, 0, 0, 8, 3, 0, 0, 0, 0, 6},
            {2, 0, 4, 5, 8, 0, 0, 11, 7, 12, 0, 10},
            {3, 5, 0, 0, 0, 1, 0, 0, 0, 11, 4, 0},
            {6, 0, 0, 3, 0, 10, 0, 0, 0, 0, 8, 0},
            {9, 0, 12, 0, 0, 11, 8, 3, 0, 0, 0, 7},
            {8, 0, 6, 4, 0, 0, 10, 9, 3, 5, 0, 0},
            {5, 4, 9, 0, 0, 0, 11, 0, 6, 2, 7, 1},
            {0, 3, 1, 0, 9, 6, 7, 5, 0, 0, 10, 8},
            {10, 0, 7, 0, 5, 2, 12, 0, 1, 3, 6, 0},};

        // Test puzzle with exactly 2 solutions
        Sudoku s = new Sudoku(sudokuWithTwoSolutions, 9, 9, 3, 3);
        SudokuSolver SS = new SudokuSolver(s);
        assertEquals(2, SS.solve(false));

        // Test puzzle with 1 solution (9X9)
        s = new Sudoku(sudokuWithOneSolution, 9, 9, 3, 3);
        SS = new SudokuSolver(s);
        assertEquals(1, SS.solve(false));

        // Test puzzle with 1 solution (4X4)
        s = new Sudoku(sudokuWithOneSolution4X4, 4, 4, 2, 2);
        SS = new SudokuSolver(s);
        assertEquals(1, SS.solve(false));

        //Test puzzle with multiple solutions (6x6, 3x2)
        // Solver stops at 2 solutions so assert equals 2
        s = new Sudoku(SudokuWithMultipleSolutions2x3, 6, 6, 2, 3);
        SS = new SudokuSolver(s);
        assertEquals(2, SS.solve(false));

        // Test puzzle with 1 solution (12x12 3x4)
        s = new Sudoku(unique12x123x4, 12, 12, 4, 3);
        SS = new SudokuSolver(s);
        assertEquals(1, SS.solve(false));

        // Test puzzle with multiple solutions (12x12 3x4)
        s = new Sudoku(notSoUnique12x12, 12, 12, 4, 3);
        SS = new SudokuSolver(s);
        assertEquals(2, SS.solve(false));
    }

}
