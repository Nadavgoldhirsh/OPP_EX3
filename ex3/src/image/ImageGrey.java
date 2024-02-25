package image;
import java.awt.*;

/**
 * This class has a method that gets image and make from it every pixel as grey
 */
public class ImageGrey {
    /**
     * class Ctor
     */
    public ImageGrey() {}

    /**
     * This method creates a grey images
     */
    public static double greyImage(Image image){
        Color color;
        double greyPixel = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                color = image.getPixel(i, j);
                greyPixel = greyPixel + color.getRed() * 0.2126 +
                        color.getGreen() * 0.7152 + color.getBlue() * 0.0722;
            }
        }

        return greyPixel / (image.getHeight() * image.getWidth()) / 255;
    }

}
