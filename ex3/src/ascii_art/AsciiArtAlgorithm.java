package ascii_art;

import image.Image;
import static image.ImageGrey.greyImage;
import static image.ImagePadder.padImage;
import static image.ImageSub.subImage;
import image_char_matching.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class has a method that run the program
 */
public class AsciiArtAlgorithm {
    private double[][] last_brightness ;
    private Image[][] subImages;
    private image.Image image;
    private int res;
    private Set<Character> charset;

    /**
     * class Ctor
     */
    public AsciiArtAlgorithm(image.Image image, int res, Set<Character> charset) {
        this.image =  padImage(image);
        this.res = res;
        this.charset = charset;
        last_brightness = null;
        subImages = null;
    }
    /**
     * This method run the program
     */
    public char [][] run(){
        SubImgCharMatcher matcher = new SubImgCharMatcher(charset);
        char[][] art = new char[image.getHeight()/(image.getWidth()/res)][res];
        if(last_brightness != null && subImages!=null){
            createFromGiven(matcher, art);
        }
        else{
             createFromScratch(matcher, art); // this method fill the art array
        }
        return art;
    }

    private void createFromGiven(SubImgCharMatcher matcher, char[][] art) {
        for (int i = 0; i < image.getHeight()/(image.getWidth()/res); i++) {
            for (int j = 0; j < res; j++) {
                char char1 = matcher.getCharByImageBrightness(last_brightness[i][j]);
                art[i][j] = char1;
            }
        }
    }

    private char[][] createFromScratch(SubImgCharMatcher matcher, char[][] art) {
        Image[][] subImages = subImage(this.res, this.image);
        last_brightness = new double[image.getHeight() / (image.getWidth() / res)][ res];
        for (int i = 0; i < image.getHeight() / (image.getWidth() / res); i++) {
            for (int j = 0; j < res; j++) {
                Image image1 = subImages[i][j];
                double brightness = greyImage(image1);
                last_brightness[i][j] = brightness;
                char char1 = matcher.getCharByImageBrightness(brightness);
                art[i][j] = char1;
            }
        }
        return art;
    }
}
