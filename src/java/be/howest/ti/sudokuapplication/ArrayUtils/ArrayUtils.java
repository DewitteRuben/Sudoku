/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.sudokuapplication.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Ruben
 */
public class ArrayUtils {
    
    public static int[][] twoDimensionalArrayCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }
    
    public static boolean[][] twoDimensionalArrayCopy(boolean[][] original) {
        if (original == null) {
            return null;
        }

        final boolean[][] result = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }
    
     public static ArrayList<Integer> getListOfUniqueRandomNumbersWithMax(int size) {

        Integer[] arrayOfNumbersTillSize = new Integer[size];

        // Fill array with numbers from 1 to size
        for (int i = 0; i < size; i++) {
            arrayOfNumbersTillSize[i] = i + 1;
        }

        // Randomize the array by shuffling it
        ArrayList<Integer> randomArray = new ArrayList<>(Arrays.asList(arrayOfNumbersTillSize));
        Collections.shuffle(randomArray);

        return randomArray;
    }
}
