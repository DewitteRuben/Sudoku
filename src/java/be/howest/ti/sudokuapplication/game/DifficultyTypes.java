package be.howest.ti.sudokuapplication.game;

import java.util.Random;

/**
 *
 * @author Ruben
 */
public enum DifficultyTypes {

    FOUR_BY_FOUR_NORMAL(8, 12),
    SIX_BY_SIX_NORMAL(22, 27),
    NINE_BY_NINE_EASY(35, 47),
    NINE_BY_NINE_NORMAL(48, 53),
    NINE_BY_NINE_HARD(54, 57),
    TWELVE_BY_TWELVE_EASY(31, 44),
    TWELVE_BY_TWELVE_NORMAL(45, 49),
    TWELVE_BY_TWELVE_HARD(54, 57),
    AMOUNT_OF_CELLS_TEST(50, 50),
    DEBUGGING(2, 2);

    private final int minimumRemovalAmount;
    private final int maximumRemovalAmount;

    DifficultyTypes(int minimumRemovalAmount, int maximumRemovalAmount) {
        this.minimumRemovalAmount = minimumRemovalAmount;
        this.maximumRemovalAmount = maximumRemovalAmount;
    }

    public int getOpenCellNumber() {
        return new Random().nextInt((maximumRemovalAmount - minimumRemovalAmount) + 1) + minimumRemovalAmount;
    }
}
