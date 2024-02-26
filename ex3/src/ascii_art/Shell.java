package ascii_art;

import image.Image;
import image_char_matching.SubImgCharMatcher;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * This class has a method that run the program
 */

public class Shell {
    /**
     * default cat image
     */
    private static String IMAGE = "cat.jpeg";
    /**
     * default resolution
     */
    private static int RESOLUTION = 128;
    /**
     * default charset
     */
    private static Set<Character> CHARSET = new HashSet<>();
    /**
     * method that run the program
     */
    public void run() throws IOException {
        int flag = 0;
        CHARSET.addAll(Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
        while (flag == 0) {
            System.out.println(">>>");
            String input = KeyboardInput.readLine();
            String[] words = input.split("\\s+");
            SubImgCharMatcher matcher = new SubImgCharMatcher(CHARSET);
            if (input.equals("exit")) {
                flag = 1;
            } else if (input.equals("chars")) {
                printChars();
            } else if (words[0].equals("add")) {
                addFun(words,matcher);
            ///////////////// missing all functions until here
            } else if (input.equals("asciiArt")){
                ///////////////// just a template
                Image image = new Image(IMAGE);
                AsciiArtAlgorithm ascii = new AsciiArtAlgorithm(image,RESOLUTION,CHARSET);
                char[][] art = ascii.run();
                for (int i = 0; i < art.length; i++) {
                    for (int j = 0; j < art[i].length; j++) {
                        System.out.print(art[i][j]);
                    }
                    System.out.println();
                }
            }
        }
    }
    /**
     * method print chars
     */
    private void printChars() {
        for (Character c : CHARSET) {
            System.out.print(c + " ");
        }
        System.out.println("");
    }

    /**
     * method that do all the add
     */
    private void addFun(String[] words, SubImgCharMatcher matcher) {
        if(words.length!=2){
            System.out.println("Did not add due to incorrect format.");
        } else if (words[1].length() == 1) {
            addOneChar(words[1].toCharArray()[0],matcher);
        }else if (words[1].equals("all")){
            addAllChars(matcher);
        }else if (words[1].equals("space")) {
            CHARSET.add(' ');
            matcher.addChar(' ');
        }else if (words[1].length() == 3) {
            if(words[1].toCharArray()[1]=='-'){
                addSomeChars(words[1].toCharArray()[0],words[1].toCharArray()[2],matcher);
            }
        } else{
            System.out.println("Did not add due to incorrect format.");
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
        CHARSET.add(character);
        matcher.addChar(character);
    }
}
