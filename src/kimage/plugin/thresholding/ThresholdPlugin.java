package kimage.plugin.thresholding;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public abstract class ThresholdPlugin extends Plugin {

    @Override
    public void process(Image imgIn, Image imgOut) {
        final int threshold = getThreshold(imgIn);
        setAttribute("threshold", threshold);
        thresholdImage(imgIn, imgOut, threshold);
    }

    /**
     * Converts an image (RGB, RGBA, ... whatever) to a binary one based on
     * given threshold
     *
     * @param image the image to convert. Remains untouched.
     * @param threshold the threshold in [0,255]
     * @return a new BufferedImage instance of TYPE_BYTE_GRAY with only 0'S and
     * 255's
     */
    private static BufferedImage thresholdImage(Image imgIn, Image imgOut, int threshold) {
        if (imgOut.getBufferedImage().getType() != BufferedImage.TYPE_BYTE_GRAY) {
            final BufferedImage result = new BufferedImage(
                    imgIn.getWidth(),
                    imgIn.getHeight(),
                    BufferedImage.TYPE_BYTE_GRAY);
            final Graphics g = result.getGraphics();
            g.drawImage(imgIn.getBufferedImage(), 0, 0, null);
            g.dispose();
            
            imgOut.setBufferedImage(result);
            System.err.println("Image was converted into BufferedImage.TYPE_BYTE_GRAY type");
        }

        BufferedImage result = imgOut.getBufferedImage();
        result.getGraphics().drawImage(imgIn.getBufferedImage(), 0, 0, null);
        WritableRaster raster = result.getRaster();
        int[] pixels = new int[imgIn.getWidth()];
        for (int y = 0; y < imgIn.getHeight(); y++) {
            raster.getPixels(0, y, imgIn.getWidth(), 1, pixels);
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = (pixels[i] < threshold) ? 0 : 255;
            }
            raster.setPixels(0, y, imgIn.getWidth(), 1, pixels);
        }
        return result;
    }

    public abstract int getThreshold(Image imgIn);
}
