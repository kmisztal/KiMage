package kimage.plugins.morphology.grayscale;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.plugin.extras.Attributes;

/**
 * @author Krzysztof
 */
public class ErosionGrayscaleImage extends Plugin {

    int maskSize = 1;

    @Override
    public void process(Image imgIn, Image imgOut) {
        if (getAttribute(Attributes.SIZE) != null) {
            maskSize = (Integer) getAttribute(Attributes.SIZE);
        } else {
            setAttribute(Attributes.SIZE, maskSize);
        }

        final int width = imgIn.getWidth();
        final int height = imgIn.getHeight();
        final int outputPixels[] = new int[width * height];

        /**
         * Median Filter operation
         */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int xMin = x - maskSize;
                int xMax = x + maskSize;
                int yMin = y - maskSize;
                int yMax = y + maskSize;
                int newCol_red = 255;
                int newCol_green = 255;
                int newCol_blue = 255;
                for (int r = yMin; r <= yMax; r++) {
                    for (int c = xMin; c <= xMax; c++) {
                        if (r >= 0 && r < height && c >= 0 && c < width) {
                            final int rgb = imgIn.getRGB(c, r);
                            final int red = (rgb >> 16 & 0xff);
                            final int green = (rgb >> 8 & 0xff);
                            final int blue = rgb & 0xff;

                            if (red < newCol_red) {
                                newCol_red = red;
                            }
                            if (green < newCol_green) {
                                newCol_green = green;
                            }
                            if (blue < newCol_blue) {
                                newCol_blue = blue;
                            }
                        }
                    }
                }
                outputPixels[x + y * width] = (0xFF000000) | (newCol_red << 16) | (newCol_green << 8) | newCol_blue;
            }
        }
        /**
         * Write the output pixels to the image pixels
         */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                imgOut.setRGB(x, y, outputPixels[x + y * width]);
            }
        }
    }

}
