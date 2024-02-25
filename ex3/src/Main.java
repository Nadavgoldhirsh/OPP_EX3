import ascii_art.*;
import image.Image;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Image image = new Image("C:\\Users\\shach\\IdeaProjects\\OPP_EX3\\ex3\\src\\examples\\board.jpeg");
        char[] charSet = {'o', 'm'};
        AsciiArtAlgorithm ascii = new AsciiArtAlgorithm(image,2,charSet);
        char[][] art = ascii.run();
    }
}