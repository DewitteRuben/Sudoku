/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.sudokuapplication.game;

/**
 *
 * @author Ruben
 */
public class Difficulty {


    private final int gridSize;
    private final String difficultyText;

    public Difficulty(int gridSize, String difficultyText) {
        this.gridSize = gridSize;
        this.difficultyText = difficultyText;
     }

    public int getAmountOfCellsToRemove() {
        switch (difficultyText) {
            case "Easy":
                switch (gridSize) {
                    case 9:
                        return DifficultyTypes.NINE_BY_NINE_EASY.getOpenCellNumber();
                    case 12:
                        return DifficultyTypes.TWELVE_BY_TWELVE_EASY.getOpenCellNumber();
                }
                break;
            case "Normal":
                switch (gridSize) {
                    case 4:
                        return DifficultyTypes.FOUR_BY_FOUR_NORMAL.getOpenCellNumber();
                    case 6:
                        return DifficultyTypes.SIX_BY_SIX_NORMAL.getOpenCellNumber();
                    case 9:
                        return DifficultyTypes.NINE_BY_NINE_NORMAL.getOpenCellNumber();
                    case 12:
                        return DifficultyTypes.TWELVE_BY_TWELVE_NORMAL.getOpenCellNumber();
                }
                break;
            case "Hard":
                switch (gridSize) {
                    case 9:
                        return DifficultyTypes.NINE_BY_NINE_HARD.getOpenCellNumber();
                    case 12:
                        return DifficultyTypes.TWELVE_BY_TWELVE_HARD.getOpenCellNumber();
                }
                break;
            case "Debugging":
                return DifficultyTypes.DEBUGGING.getOpenCellNumber();
            case "GiveCellsTest":
                return DifficultyTypes.AMOUNT_OF_CELLS_TEST.getOpenCellNumber();
                
        }
        return -1;
    }
 
 }


