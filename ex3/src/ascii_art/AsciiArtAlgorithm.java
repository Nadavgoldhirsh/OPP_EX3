package ascii_art;

import image.Image;
import static image.ImageGrey.greyImage;
import static image.ImagePadder.padImage;
import static image.ImageSub.subImage;
import image_char_matching.*;

import java.util.Set;

/**
 * This class has a method that run the program
 */
public class AsciiArtAlgorithm {

    private image.Image image;
    private int res;
    private Set<Character> charset;

    /**
     * class Ctor
     */
    public AsciiArtAlgorithm(image.Image image, int res, Set<Character> charset) {
        this.image = image;
        this.res = res;
        this.charset = charset;
    }
    /**
     * This method run the program
     */
    public char [][] run(){
        SubImgCharMatcher matcher = new SubImgCharMatcher(charset);
        this.image = padImage(this.image);
        Image[][] subImages = subImage(this.res, this.image);
        char[][] art = new char[image.getHeight()/(image.getWidth()/res)][res];
        for (int i = 0; i < image.getHeight()/(image.getWidth()/res); i++) {
            for (int j = 0; j < res; j++) {
                Image image1 = subImages[i][j];
                double brightness = greyImage(image1);
                char char1 = matcher.getCharByImageBrightness(brightness);
                art[i][j] = char1;
            }
        }
        return art;
    }
}
