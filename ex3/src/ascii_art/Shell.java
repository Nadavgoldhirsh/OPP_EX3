package ascii_art;

import ascii_output.AsciiOutput;
import exceptions.InvalidUserInputException;
import image.ImagePadder;
import output_factory.FactoryAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class has a method that run the program
 */

public class Shell {
    /**
     * a tag in order to create the specific AsciiOutput type, this one is the tag for the console
     */
    public static final String CONSOLE = "console";
    /**
     * a tag in order to create the specific AsciiOutput type, this one is the tag for the html
     */
    public static final String HTML = "html";
    private static final double MAX_WORDS_AMOUNT = 2;
    private static final String GET_CHARS_LIST = "chars";
    private static final String MAKE_ASCIIART = "asciiArt";
    private static final String INCORRECT_FORMAT_NO_ADD = "Did not add due to incorrect format.";
    private static final String ADD_ALL = "all";
    private static final String SPACE = "space";
    private static final char SPACE_BAR = ' ';
    private static final int START_ASCII_VAL = 32;
    private static final int END_ASCII_VAL = 126;
    private static final int AMOUNT_OF_CHARS_IN_RANGE_CASE = 3;
    private static final char MINUS_KEY = '-';
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";
    private static final String RES = "res";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String RES_INCORRECT_FORMAT = "Did not change resolution due to incorrect format";
    private static final int RES_MULT = 2;
    private static final String OUSTIDE_RES_RANGE_MSG = "Did not change resolution due to exceeding boundaries.";
    private static final String IMAGE_CHANGE = "image";
    private static final String IMAGE_FILE_FAIL = "Did not execute due to problem with image file.";
    private static final String DEFAULT_OUTPUT = "console";
    private static final String OUTPUT = "output";
    private static final String OUTPUT_INCORRECT_FORMAT = "Did not change output method due to incorrect format.";

    private static final String EMPTY_CHARSET_MSG = "Did not execute. Charset is empty.";
    private static final String DEFAULT_IMAGE_PATH_IS_INVALID = "default image path is invalid";
    private static final String INCORRECT_COMMAND_MSG = "Did not execute due to incorrect command.";
    private static final String DID_NOT_CHARS_DUE_TO_INCORRECT_FORMAT = "Did not chars due to incorrect format.";
    private static final String REMOVE_INCORRECT_FORMAT = "Did not remove due to incorrect format.";
    private static final String DID_NOT_IMAGE_DUE_TO_INCORRECT_FORMAT = "Did not image due to incorrect format.";
    private static final String DID_NOT_RES_DUE_TO_INCORRECT_FORMAT = "Did not change resolution due to incorrect format.";
    private static final String INVALID_ASCII_ART_COMMAND_FORMAT = "Invalid AsciiArt command format";


    /**
     * default cat image
     */
    private static final String IMAGE = "cat.jpeg";
    private static final String OUTED_FOR_INPUT = ">>> ";
    private static final String RESOLUTION_SET_TO = "Resolution set to ";
    /**
     * default resolution
     */
    private static final int DEFAULT_RESOLUTION = 128;
    /**
     * default charset
     */
    private static final Set<Character> charSet = new TreeSet<>((c1, c2) -> Character.compare((char) c1, (char) c2));
    /**
     * method that run the program
     */
    private static final String EXIT = "exit";
    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String REGEX = "\\s+";
    private static final String DOT = ".";
    private int resolution;
    private Image image;
    private String outputType;
    private final FactoryAsciiOutput factory;
    private AsciiOutput asciiOuter;
    private AsciiArtAlgorithm ascii;
    private boolean changedTheAlgoParams;

    public Shell() throws IOException{
        resolution = DEFAULT_RESOLUTION;
        outputType = DEFAULT_OUTPUT;
        charSet.addAll(Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
        image = ImagePadder.padImage(new Image(IMAGE));
        factory = new FactoryAsciiOutput();
        outputType = DEFAULT_OUTPUT;
        asciiOuter = factory.createAsciiOutput(outputType);
        ascii = new AsciiArtAlgorithm(image,resolution,charSet);
        changedTheAlgoParams = false;
    }

    private char[] getCharsInArray() {
        char[] charArray = new char[charSet.size()];
        int index = 0;
        for (char c : charSet) {
            charArray[index++] = c;
        }
        return charArray;
    }

    public void run() {
        char[] charArray = getCharsInArray();
        SubImgCharMatcher matcher = new SubImgCharMatcher(charArray);
        while (true) {
            System.out.print(OUTED_FOR_INPUT);
            String input = KeyboardInput.readLine();
            String[] words = input.split(REGEX);
            try{
            if (input.equals(EXIT)) {
                break;
            }
            else if (words[0].equals(GET_CHARS_LIST)) {
                printChars(words);
            }
            else if (words[0].equals(ADD)) {
                addFun(words,matcher);
            }
            else if (words[0].equals(REMOVE)){
                removeFunc(words, matcher);
            } else if (words[0].equals(RES)) {
                resFunc(words);
            } else if (words[0].equals(IMAGE_CHANGE)) {
                changeImageFunc(words);
            } else if (words[0].equals(OUTPUT)) {
                outputChangeFunc(words);
            } else if (words[0].equals(MAKE_ASCIIART)){
                runFunc(words);
            }
            else{
                throw new InvalidUserInputException(INCORRECT_COMMAND_MSG);
            }
        }
            catch (InvalidUserInputException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static void main(String[] args){
        try{
            Shell myShell = new Shell();
            myShell.run();
        }
        catch (IOException e){
            System.out.println(DEFAULT_IMAGE_PATH_IS_INVALID);
        }
    }

    /**
     * This method runs the AsciiAlgorithm with the current image, res and charSet
     */
    private void runFunc(String[] words) throws InvalidUserInputException{
        if (words.length != 1){
            throw new InvalidUserInputException(INVALID_ASCII_ART_COMMAND_FORMAT);
        }
        if (charSet.isEmpty()){
            throw new InvalidUserInputException(EMPTY_CHARSET_MSG);
        }
        if (!changedTheAlgoParams){
            char[][] art = ascii.run();
            changedTheAlgoParams = false;
            asciiOuter.out(art);
        }
        else {
            AsciiArtAlgorithm ascii = new AsciiArtAlgorithm(image, resolution, charSet);
            char[][] art = ascii.run();
            asciiOuter.out(art);
            changedTheAlgoParams = false;
        }
    }

    /**
     * This method changes the output
     * @param words the array of strings given by the user
     */
    private void outputChangeFunc(String[] words) throws InvalidUserInputException {
        if (words.length == 1 || words.length> MAX_WORDS_AMOUNT){
            throw new InvalidUserInputException(OUTPUT_INCORRECT_FORMAT);
        }
        else if (words[1].equals(CONSOLE)){
            outputType = CONSOLE;
        } else if (words[1].equals(HTML)) {
            outputType = HTML;
        }
        else{
            throw new InvalidUserInputException(OUTPUT_INCORRECT_FORMAT);
        }
        asciiOuter = factory.createAsciiOutput(outputType);
    }

    /**
     * This method changes the image
     * @param words the array of strings given by the user
     */
    private void changeImageFunc(String[] words)  throws InvalidUserInputException{
        if (words.length>MAX_WORDS_AMOUNT){
            throw new InvalidUserInputException(DID_NOT_IMAGE_DUE_TO_INCORRECT_FORMAT);
        }
        try {
            image = new Image(words[1]);
            changedTheAlgoParams = true;

        }
        catch (IOException e){
            throw new InvalidUserInputException(IMAGE_FILE_FAIL);
        }
    }

    /**
     * This func changes the res of the class according to the limitation of the given image.
     *
     * @param words - the string array given by the user
     */
    private void resFunc(String[] words) throws InvalidUserInputException{
        if (words.length==1 ){
            throw new InvalidUserInputException(RES_INCORRECT_FORMAT);
        } else if (words.length > MAX_WORDS_AMOUNT) {
            throw new InvalidUserInputException(DID_NOT_RES_DUE_TO_INCORRECT_FORMAT);
        }
        // the length is 2, we check for up and down
        else if (words[1].equals(UP)) {
            upCase();
        } else if (words[1].equals(DOWN)) {
            downCase();
        }
        else{
            throw new InvalidUserInputException(DID_NOT_RES_DUE_TO_INCORRECT_FORMAT);
        }
    }

    private void downCase() throws InvalidUserInputException {
        int min_res = Math.max(1, image.getWidth()/ image.getHeight());
        if (resolution / RES_MULT < min_res){
            throw new InvalidUserInputException(OUSTIDE_RES_RANGE_MSG);
        }
        else{
            resolution = resolution/2;
            changedTheAlgoParams = true;
            System.out.println(RESOLUTION_SET_TO +resolution+DOT);
        }
    }

    private void upCase() throws InvalidUserInputException {
        int max_res = image.getWidth();
        if (resolution* RES_MULT> max_res){
            throw new InvalidUserInputException(OUSTIDE_RES_RANGE_MSG);
        }
        else{
            resolution = resolution*2;
            changedTheAlgoParams = true;
            System.out.println(RESOLUTION_SET_TO+resolution+ DOT);
        }
    }

    private void removeFunc(String[] words, SubImgCharMatcher matcher) throws InvalidUserInputException{
        if(words.length>MAX_WORDS_AMOUNT){
            throw new InvalidUserInputException(REMOVE_INCORRECT_FORMAT);
        } else if (words[1].length() == 1) {
            removeOneChar(words[1].toCharArray()[0], matcher);
        }else if (words[1].equals(ADD_ALL)){
            removeAllChars(matcher);
        }else if (words[1].equals(SPACE)) {
            charSet.add(SPACE_BAR);
            matcher.addChar(SPACE_BAR);
        }else if (words[1].length() == AMOUNT_OF_CHARS_IN_RANGE_CASE) {
            if(words[1].toCharArray()[1]=='-'){
                removeSomeChars(words[1].toCharArray()[0], words[1].toCharArray()[AMOUNT_OF_CHARS_IN_RANGE_CASE-1], matcher);
            }
            else{
                throw new InvalidUserInputException(REMOVE_INCORRECT_FORMAT);
            }
        } else{
            throw new InvalidUserInputException(REMOVE_INCORRECT_FORMAT);
        }
    }

    /**
     * This method removes the chars for the given range
     * @param a the end/start char of the range
     * @param b the end/start char of the range
     * @param matcher the matcher obj to remove from
     */

    private void removeSomeChars(char a, char b, SubImgCharMatcher matcher) {
        if((int)b >= (int)a){
            for (int i = a; i <= b; i++){
                removeOneChar((char)i, matcher);
            }
        }
        else {
            for (int i = b; i <= a; i++){
                removeOneChar((char)i, matcher);
            }
        }
    }

    /**
     * This method removes all the chars by calling the method that removes on all the chars
     * @param matcher the given SubImgCharMatcher
     */
    private void removeAllChars(SubImgCharMatcher matcher) {
        for (int i = START_ASCII_VAL; i <= END_ASCII_VAL; i++){
            removeOneChar((char)i, matcher);
        }
    }

    /**
     * This method removes one char from the set in matcher and the set in this class
     * @param c the char to add
     * @param matcher the matcher obj
     */
    private void removeOneChar(char c, SubImgCharMatcher matcher) {
        charSet.remove(c);
        matcher.removeChar(c);
    }

    /**
     * method print chars
     */
    private void printChars(String[] words) throws InvalidUserInputException {
        if (words.length>1){
            throw new InvalidUserInputException(DID_NOT_CHARS_DUE_TO_INCORRECT_FORMAT);
        }
        for (Character c : charSet) {
            System.out.print(c + SPACE_STRING);
        }
        System.out.println(EMPTY_STRING);
    }

    /**
     * method that do all the add
     * @param words an array of strings representing the users input
     * @param matcher a matcher to add the chars to
     */
    private void addFun(String[] words, SubImgCharMatcher matcher) throws  InvalidUserInputException{
        if(words.length!=MAX_WORDS_AMOUNT){
            throw new InvalidUserInputException(INCORRECT_FORMAT_NO_ADD);
        } else if (words[1].length() == 1) {
            addOneChar(words[1].toCharArray()[0],matcher);
        }else if (words[1].equals(ADD_ALL)){
            addAllChars(matcher);
        }else if (words[1].equals(SPACE)) {
            charSet.add(SPACE_BAR);
            matcher.addChar(SPACE_BAR);
        }else if (words[1].length() == AMOUNT_OF_CHARS_IN_RANGE_CASE) {
            if(words[1].toCharArray()[1]== MINUS_KEY){
                addSomeChars(words[1].toCharArray()[0],words[1].toCharArray()[AMOUNT_OF_CHARS_IN_RANGE_CASE-1],matcher);
            }
            else{
                throw new InvalidUserInputException(INCORRECT_FORMAT_NO_ADD);
            }
        } else{
            throw new InvalidUserInputException(INCORRECT_FORMAT_NO_ADD);
        }
    }

    /**
     * method that add some chars
     */
    private void addSomeChars(char a, char b,SubImgCharMatcher matcher) {
        if((int)b >= (int)a){
            for (int i = a; i <= b; i++){
                addOneChar((char)i, matcher);
            }
        }
        else {
            for (int i = b; i <= a; i++){
                addOneChar((char)i, matcher);
            }
        }
    }

    /**
     * method that add all chars
     */
    private void addAllChars(SubImgCharMatcher matcher) {
        for (int i = 32; i <= 126; i++){
            addOneChar((char)i, matcher);
        }
    }

    /**
     * method that add one char
     */
    private void addOneChar(char character, SubImgCharMatcher matcher) {
        charSet.add(character);
        matcher.addChar(character);
    }
}
