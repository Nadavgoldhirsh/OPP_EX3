package image;

import java.awt.*;

/**
 * This class has a method that gets image and make from it sub images
 */
public class ImageSub {
    /**
     * class Ctor
     */
    public ImageSub() {}

    /**
     * This method creates a sub images
     */
    public static Image[][] subImage(int res, Image image){
        int subImageDim = image.getWidth()/res;
        int newArrayHeight = image.getHeight()/(subImageDim);
        Image [][] subImages = new Image[newArrayHeight][res];
        for (int i = 0; i < newArrayHeight; i++) {
            for (int j = 0; j < res; j++) {
                Color [][] pixelArray = new Color[subImageDim][subImageDim];
                for (int a = 0; a < subImageDim; a++) {
                    for (int b = 0; b < subImageDim; b++) {
                        pixelArray[a][b] = image.getPixel
                                (a + subImageDim * j, b + subImageDim * i);
                    }
                }
                Image sub = new Image(pixelArray, subImageDim, subImageDim);
                subImages [i][j] = sub;
            }
        }
        return subImages;
    }
}
