/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import be.howest.ti.sudokuapplication.ConsoleUI.consoleDisplay;
import be.howest.ti.sudokuapplication.game.Sudoku;
import be.howest.ti.sudokuapplication.game.SudokuSolver;
import com.sun.org.apache.xpath.internal.operations.Equals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ruben
 */
public class SudokuTest {

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

    /*








     */
    int[][] sudokuWith2Solutions = new int[][]{
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

    @Test
    public void TestNewMove() {
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

        // Check cell value
        assertEquals(s.getCellValue(0, 1), 0);

        // Change same cell value
        s.makeNewMove(0, 1, 2);

        // Check cell value again
        assertEquals(s.getCellValue(0, 1), 2);

        ////////////////////////////////////////////////
        // Check cell value
        assertEquals(s.getCellValue(0, 2), 9);

        // Change same cell value
        s.makeNewMove(0, 2, 2);

        // Check cell value again
        assertNotEquals(s.getCellValue(0, 2), 2);

    }

    @Test
    public void TestChangeableCells() {
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

        // Assure true if cell is changeable
        assertTrue(s.isChangableCell(0, 1));
        assertTrue(s.isChangableCell(6, 4));

        // Assure false if cell is not changeable
        assertFalse(s.isChangableCell(0, 0));
        assertFalse(s.isChangableCell(3, 6));

    }

    @Test
    public void TestClear() {
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

        // Add some values
        s.makeNewMove(0, 1, 2);
        s.makeNewMove(3, 3, 2);
        s.makeNewMove(0, 1, 8);
        s.makeNewMove(4, 1, 4);
        s.makeNewMove(0, 3, 2);
        s.makeNewMove(5, 6, 8);
        s.makeNewMove(8, 4, 6);

        // Clear after adding
        s.clear();

        // Check if cleared
        boolean cleared = true;
        int[][] grid = s.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (s.isChangableCell(i, j) && grid[i][j] > 0) {
                    cleared = false;
                }
            }
        }

        assertTrue(cleared);

    }

    @Test
    public void TestClearAll() {
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

        // Add some values
        s.makeNewMove(0, 1, 2);
        s.makeNewMove(3, 3, 2);
        s.makeNewMove(0, 1, 8);
        s.makeNewMove(4, 1, 4);
        s.makeNewMove(0, 3, 2);
        s.makeNewMove(5, 6, 8);
        s.makeNewMove(8, 4, 6);

        // Clear after adding
        s.clearAll();

        // Check if cleared
        boolean cleared = true;
        int[][] grid = s.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] > 0) {
                    cleared = false;
                }
            }
        }

        assertTrue(cleared);

    }

    @Test
    public void TestFillChangableCells() {
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

        // Check if every filled in cell is actually unmutable
        int[][] grid = s.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] > 0) {
                    assertFalse(s.isChangableCell(i, j));
                }
            }
        }
    }

    //Partly a SudokuSolver test
    @Test
    public void TestFillSolution() {
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

        Sudoku solvedSudoku = new Sudoku(solvedVersion, 9, 9, 3, 3);
        Sudoku UnSolvedVersion = new Sudoku(unSolvedValidSudoku, 9, 9, 3, 3);
        int[][] regularGridFromSolved = solvedSudoku.getGrid();
        int[][] solutionGridFromUnsolved = UnSolvedVersion.getSolutionGrid();

        boolean equal = true;
        // Check if solution is expected solution
        for (int i = 0; i < regularGridFromSolved.length; i++) {
            for (int j = 0; j < regularGridFromSolved[0].length; j++) {
                if (regularGridFromSolved[i][j] != solutionGridFromUnsolved[i][j]) {
                    equal = false;

                }
            }
        }
        assertTrue(equal);
    }
}
