package image;
import java.awt.*;

/**
 * This class has a method that gets image and make from it every pixel as grey
 */
public class ImageGrey {

    private static final double RED = 0.2126;
    private static final double GREEN = 0.7152;
    private static final double BLUE = 0.0722;
    private static final int CHARSAMOUNT = 255;

    /**
     * class Ctor
     */
    public ImageGrey() {}

    /**
     * This method creates a grey images
     */
    public static double greyImage(Image subImage){
        Color color;
        double sumGreyPixel = 0;
        for (int i = 0; i < subImage.getHeight(); i++) {
            for (int j = 0; j < subImage.getWidth(); j++) {
                color = subImage.getPixel(i, j);
                sumGreyPixel +=  color.getRed() * RED +
                        color.getGreen() * GREEN + color.getBlue() * BLUE;
            }
        }
        int pixelAmount = (subImage.getHeight() * subImage.getWidth());
        return (sumGreyPixel /  pixelAmount)/ CHARSAMOUNT;
    }

}
