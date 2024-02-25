import ascii_art.*;
import image.Image;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Image image = new Image("cat.jpeg");
        char[] charSet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
//        char[] charSet = {'o', 'm'};
//        char[] charSet = {'-', '@'};
//        char[] charSet = new char[300];
//        for (int i = 0; i < 250; i++) {
//            charSet[i] = (char) (Character.MIN_VALUE + i);
//        }
        AsciiArtAlgorithm ascii = new AsciiArtAlgorithm(image,128,charSet);
        char[][] art = ascii.run();
    }
}