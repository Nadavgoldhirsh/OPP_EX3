package image;

import java.awt.*;

/**
 * This class has a method that gets image and returns a new one after being padded
 */
public class ImagePadder {
    /**
     * class Ctor
     */
    public ImagePadder() {}
    private static boolean isPowerOfTwo(int x){
        return   (x & (x - 1)) == 0 && x != 0;
    }

    /**
     * This method creates a new padded image
     * @param image - a given image instance to pad whose width and height are even numbers
     * @return a padded image
     */
    public static Image padImage(Image image){
        int newArrayHeight = image.getHeight();
        int newArrayWidth = image.getWidth();
        if (isPowerOfTwo(newArrayHeight) && isPowerOfTwo(newArrayWidth)){return image;}
        if (!isPowerOfTwo(newArrayHeight)) {
            newArrayHeight = getNextPowerOfTwo(newArrayHeight);
        }
        if (!isPowerOfTwo(newArrayWidth)) {
            newArrayWidth = getNextPowerOfTwo(newArrayWidth);
        }
        Color [][] pixelArray = new Color[newArrayHeight][newArrayWidth];
        int heightPad =  (newArrayHeight- image.getHeight())/2;
        int widthPad =  (newArrayWidth- image.getWidth())/2;
        for (int i = 0; i < newArrayHeight; i++) {
            for (int j = 0; j < newArrayWidth; j++) {
                if (((heightPad <= i) && (i < heightPad + image.getHeight())) &&
                        ((widthPad <= j) && (j < widthPad + image.getWidth()))){
                    Color color = image.getPixel(i-heightPad,j-widthPad);
                    pixelArray[i][j] = color;
                }
                else{
                    pixelArray[i][j] = Color.WHITE;
                }
            }
        }
        Image i = new Image(pixelArray,newArrayWidth,newArrayHeight);
        return i;
    }

    /**
     * This method returns the power of two that is bigger than x
     * @param x an int that we want to get the power of two that is bigger than it
     * @return an int
     */
    private static int getNextPowerOfTwo(int x) {
        int nextPowerOfTwo = 1;
        while (nextPowerOfTwo < x) {
            nextPowerOfTwo *=2;
        }
        return nextPowerOfTwo;
    }
}
