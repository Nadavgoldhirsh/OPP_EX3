package image_char_matching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class holds a Map of the chars to build the Ascii art from.
 * it holds them in a Map where the keys are the chars and the values are their brightness levels.
 */
public class SubImgCharMatcher {
    private static final int CHARS_AMOUNT = 256;
    /**
     * This is the Map that holds the chars and their brightness
     */
    private final HashMap<Character, Double> charMap = new HashMap<>();
    private final static double INIT_BRIGHT = -1;
    private final static int BOOL_ARR_SIZE = 16;


    /**
     * Ctor of the class that gets a set of chars to use
     * @param charset a set of chars to use in order to create the new image
     */
    public SubImgCharMatcher(char[] charset){
        for (char ch : charset){
            charMap.put(ch, INIT_BRIGHT) ;
        }
        calcCharSetBrightness();
    }

    /**
     * This method returns the char with the closest brightness level to the input brightness
     * @param brightness the desired brightness level
     * @return a char from the given set that his brightness the closest to the
     * desired one, if there are several chars like that we return the one with the
     * lowest Ascii value
     */
    public char getCharByImageBrightness(double brightness){
        LinkedList<Character> bestChars = new LinkedList<>();
        double bestDistance = 1;  // will be replaced because vals in range 0-1
        for (Map.Entry<Character, Double> entry : charMap.entrySet()) {
            char key = entry.getKey();
            double value = entry.getValue();
            if (Math.abs(value-brightness)==bestDistance){
                bestChars.add(key); //found a key that has the same distance so add it to the list
            }
            else if( Math.abs(value-brightness)<bestDistance) { // need to replace the list with the new key
                int size = bestChars.size();
                for (int i = 0; i < size; i++) {
                    bestChars.remove(0);
                }
                bestChars.add(key);
            }
        }
        // now we have a new list that contains all keys with the best distances
        return getMinAsciiValCharFromList(bestChars);
    }

    /**
     * This method returns the minimal Ascii value of the chars in the given list
     * @param bestChars a LinkedList of chars
     * @return the char that have minimal Ascii value
     */
    private static char getMinAsciiValCharFromList(LinkedList<Character> bestChars) {
        char retChar = 0;
        int bestAsciiVal = CHARS_AMOUNT; // will be replaced because chars are in range 0-256
        for (char curChar : bestChars) {
            if (bestAsciiVal > curChar) {
                bestAsciiVal = curChar;
                retChar = curChar;
            }
        }
        return retChar;
    }

    /**
     * This method calculates the brightness for every single char in the current set
     * acc to the ex requirements
     */
    private void calcCharSetBrightness() {
        for (Map.Entry<Character, Double> entry : charMap.entrySet()) {
            char key = entry.getKey();
            double value = entry.getValue();
            if(value == INIT_BRIGHT){
                charMap.put(key, getCharBrightness(key));
            }

        }  // now we assume all data is updated and we norm all the vals in the map
        for (Map.Entry<Character, Double> entry : charMap.entrySet()) {
            char key = entry.getKey();
            double value = entry.getValue();
            double minBright = getMinBrightness();
            double maxBright = getMaxBrightness();
            value = (value-minBright)/(maxBright-minBright); // calc the new norm
            charMap.put(key,value);
        }
    }

    /**
     * This func return the min brightness in the Map
     * @return the minimum value in the map assuming the vals range is 0-1
     */
    private double getMinBrightness() {
        double min = 1; //will be replaced because the range of vals are 0-1.
        for (Map.Entry<Character, Double> entry : charMap.entrySet()) {
            double value = entry.getValue();
            if (value<min) {
                min = value;
            }
        }
        return min;
    }
    /**
     * This func return the max brightness in the Map
     * @return the Max value in the map assuming the vals range is 0-1
     */
    private double getMaxBrightness() {
        double max = -1; //will be replaced because the range of vals are 0-1.
        for (Map.Entry<Character, Double> entry : charMap.entrySet()) {
            double value = entry.getValue();
            if (value>max) {
                max = value;
            }
        }
        return max;
    }


    /**
     * This method returns the brightness of a single char
     * @param key the char we want to calc its brightness
     * @return a double number from 0 to 1 that represents the brightness of the char
     */
    private static double getCharBrightness( char key) {
        boolean [][] boolArray = CharConverter.convertToBoolArray(key);
        int whiteCounter = 0;
        for (int i = 0; i < BOOL_ARR_SIZE; i++) {
            for (int j = 0; j < BOOL_ARR_SIZE; j++) {
                if (boolArray[i][j]) {
                    whiteCounter++;
                }
            }
        }
        return (double) whiteCounter /(BOOL_ARR_SIZE*BOOL_ARR_SIZE);
    }

    /**
     * This method add a char to the current set
     * @param c a char to add to the set
     */
    public void addChar(char c){

        charMap.put(c, (double) -1);
        calcCharSetBrightness();

    }

    /**
     * This method removes a char from the set and then calc the new brightness for the set
     * @param c a char to remove from the set
     */
    public void removeChar(char c){
        charMap.remove(c);
        calcCharSetBrightness();

    }
}
