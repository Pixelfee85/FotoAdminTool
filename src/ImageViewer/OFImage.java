package ImageViewer;

import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * OFImage is a class that defines an image in OF (Objects First) format.
 *
 * @author Michael Kölling and David J. Barnes.
 * @version 1.0
 */
public class OFImage extends BufferedImage {
    /**
     * Create an OFImage copied from a BufferedImage.
     *
     * @param image The image to copy.
     */
    public OFImage(BufferedImage image) {
        super(image.getColorModel(), image.copyData(null),
                image.isAlphaPremultiplied(), null);
    }

    public BufferedImage getScaledInstance(BufferedImage image, int maxSize){
        BufferedImage inputImg = image;
        BufferedImage outputImg = null;


        int resWidth = 64;
        int resHeight = 64;

        int origWidth = inputImg.getWidth();
        int origHeight = inputImg.getHeight();

        //check if scale is needed
        if(origWidth <= maxSize && origHeight <= maxSize){
            return  inputImg;
        } else {
            Scalr.Mode scaleMode = Scalr.Mode.AUTOMATIC;

            int maxSize1 = 0;
            if (origHeight < origWidth){
                scaleMode = Scalr.Mode.FIT_TO_WIDTH;
                } else if (origWidth < origHeight){
                scaleMode = Scalr.Mode.FIT_TO_HEIGHT;
              }
            return  Scalr.resize(inputImg, Scalr.Method.SPEED, scaleMode, maxSize);
        }
    }

    /**
     * Create an OFImage with specified size and unspecified content.
     *
     * @param width  The width of the image.
     * @param height The height of the image.
     */
    public OFImage(int width, int height) {
        super(width, height, TYPE_INT_RGB);
    }

    /**
     * Set a given pixel of this image to a specified color. The
     * color is represented as an (r,g,b) value.
     *
     * @param x   The x position of the pixel.
     * @param y   The y position of the pixel.
     * @param col The color of the pixel.
     */
    public void setPixel(int x, int y, Color col) {
        int pixel = col.getRGB();
        setRGB(x, y, pixel);
    }

    /**
     * Get the color value at a specified pixel position.
     *
     * @param x The x position of the pixel.
     * @param y The y position of the pixel.
     * @return The color of the pixel at the given position.
     */
    public Color getPixel(int x, int y) {
        int pixel = getRGB(x, y);
        return new Color(pixel);
    }

    //makes the image a bit darker
    public void darker() {
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setPixel(x, y, getPixel(x, y).darker());
            }
        }
    }

    //makes the image a bit darker
    public void brighter() {
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setPixel(x, y, getPixel(x, y).brighter());
            }
        }
    }

    public void threshold() {
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int greyscale = 0;
                //int[3]grb;

                //for(int i = 0; i < 3; i++){
                greyscale += (getPixel(x, y)).getRed();
                greyscale += getPixel(x, y).getGreen();
                greyscale += getPixel(x, y).getBlue();
                System.out.println("greyscale without alpha: " + greyscale);

                setPixel(x, y, getPixel(x, y).brighter());
            }
        }
    }
}