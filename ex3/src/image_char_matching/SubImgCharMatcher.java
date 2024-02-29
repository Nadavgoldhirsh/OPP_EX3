package image_char_matching;

import java.util.*;

/**
 * This class holds a Map of the chars to build the Ascii art from.
 * it holds them in a Map where the keys are the chars and the values are their brightness levels.
 */
public class SubImgCharMatcher {
    /**
     * This is the amount of chars
     */
    private static final int CHARS_AMOUNT = 256;

    /**
     * This is the Map that holds the chars and their brightness
     */
    private final HashMap<Character, Double> charMap = new HashMap<>();

    /**
     * This is the Map that holds the chars and their normal brightness
     */
    private final HashMap<Character, Double> charMapNormal = new HashMap<>();



    /**
     * arr size
     */
    private final static int BOOL_ARR_SIZE = 16;


    /**
     * Ctor of the class that gets a set of chars to use
     * @param charset a set of chars to use in order to create the new image
     */
    public SubImgCharMatcher(Set<Character> charset){
        for (char ch : charset){
            charMap.put(ch, getCharBrightness(ch));
        }
        calcNormCharSetBrightness(); // calc the norm of the vals
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
        for (Map.Entry<Character, Double> entry : charMapNormal.entrySet()) {
            char key = entry.getKey();
            double value = entry.getValue();
            if (Math.abs(value-brightness)==bestDistance){
                bestChars.add(key); //found a key that has the same distance so add it to the list
            }
            else if( Math.abs(value-brightness)<bestDistance) { // need to replace the list with the new key
                bestDistance = Math.abs(value-brightness);
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
     * @param bestChars a LinkedList of chars that isn't empty
     * @return the char that have minimal Ascii value
     */
    private static char getMinAsciiValCharFromList(LinkedList<Character> bestChars) {
        char minChar = bestChars.getFirst(); // Initialize with the first character
        for (char ch : bestChars) {
            if (ch < minChar) {
                minChar = ch; // Update minChar if the current character has a lower ASCII value
            }
        }
        return minChar;
    }

    /**
     * This method calculates the brightness for every single char in the current set
     * acc to the ex requirements
     */
    private void calcNormCharSetBrightness() {
        // now we assume all data is updated and we norm all the vals in the map
        double minBright = getMinBrightness(charMap);
        double maxBright = getMaxBrightness(charMap);
        for (Map.Entry<Character, Double> entry : charMap.entrySet()) {
            char key = entry.getKey();
            double value = entry.getValue();
            value = (value-minBright)/(maxBright-minBright); // calc the new norm
            charMapNormal.put(key,value);
        }
    }

    /**
     * This func return the min brightness in the Map
     * @return the minimum value in the map assuming the vals range is 0-1
     */
    private double getMinBrightness(HashMap<Character, Double> cmap) {
        double minBrightness = Double.MAX_VALUE;
        for (double brightness : cmap.values()) {
            if (brightness < minBrightness) {
                minBrightness = brightness;
            }
        }
        return minBrightness;
    }
    /**
     * This func return the max brightness in the Map
     * @return the Max value in the map assuming the vals range is 0-1
     */
    private double getMaxBrightness(HashMap<Character, Double> cmap) {
        double maxBrightness = Double.MIN_VALUE;
        for (double brightness : cmap.values()) {
            if (brightness > maxBrightness) {
                maxBrightness = brightness;
            }
        }
        return maxBrightness;
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
        double brightness;
        if (!charMap.containsKey(c)) {
            brightness = getCharBrightness(c);
            charMap.put(c, brightness);
        }
        else {
            // the char is already inside
            return;
        }
        // now add to the normMap and check if we need to change all or just add new norm
        if (getMaxBrightness(charMap)==brightness || getMinBrightness(charMap) == brightness){
            // means there is a new min/max value so update the whole set
            charMapNormal.put(c,brightness);
            calcNormCharSetBrightness();
        }
        else{
            double minBright = getMinBrightness(charMap);
            double maxBright = getMaxBrightness(charMap);
            double value = (charMap.get(c)-minBright)/(maxBright-minBright); // calc the new norm
            charMapNormal.put(c,value);
        }

    }

    /**
     * This method removes a char from the set and then calc the new brightness for the set
     * @param c a char to remove from the set
     */
    public void removeChar(char c){
        double brightness;
        if (charMap.containsKey(c)) {
            brightness = charMap.get(c);
            if (brightness == getMinBrightness(charMap)|| brightness == getMaxBrightness(charMap)){
                charMapNormal.remove(c);
                charMap.remove(c);
                calcNormCharSetBrightness();
            }
            else{
                charMap.remove(c);
                charMapNormal.remove(c);
            }
            }
        // else we have nothing to remove so return
        }
}
