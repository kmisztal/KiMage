package kimage.plugins.morphology;

import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 * (dodawanie, max)
 *
 * @author Krzysztof
 */
public class Dilation extends Plugin {

    int maskSize = 1;

    @Override
    public void process(Image imgIn, Image imgOut) {

        if (getAttribute("size") != null) {
            maskSize = (Integer) getAttribute("size");
        } else {
            setAttribute("size", maskSize);
        }

        final int width = imgIn.getWidth();
        final int height = imgIn.getHeight();
        final int outputPixels[] = new int[width * height];

        int xMin, xMax, yMin, yMax;

        int newCol;

        /**
         * Median Filter operation
         */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                xMin = x - maskSize;
                xMax = x + maskSize;
                yMin = y - maskSize;
                yMax = y + maskSize;
                newCol = 0;
                outer:
                for (int r = yMin; r <= yMax; r++) {
                    for (int c = xMin; c <= xMax; c++) {
                        if (r >= 0 && r < height && c >= 0 && c < width) {
                            if (imgIn.getRed(c, r) == 255) {
                                newCol = 255;
                                break outer;
                            }
                        }
                    }
                }
                outputPixels[x + y * width] = newCol;
            }
        }
        /**
         * Write the output pixels to the image pixels
         */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int c = outputPixels[x + y * width];
                imgOut.setRGB(x, y, c, c, c);
            }
        }
    }

}
