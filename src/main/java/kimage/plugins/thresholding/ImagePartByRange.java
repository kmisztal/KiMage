package kimage.plugins.thresholding;

import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 * @author Krzysztof
 */
public class ImagePartByRange extends Plugin {

    private int min = 0, max = 127;

    @Override
    public void process(Image imgIn, Image imgOut) {
        if (getAttribute("min") != null) {
            min = (int) getAttribute("min");
        } else {
            setAttribute("min", min);
        }

        if (getAttribute("max") != null) {
            max = (int) getAttribute("max");
        } else {
            setAttribute("max", max);
        }

        for (int x = 0; x < imgIn.getWidth(); x++) {
            for (int y = 0; y < imgIn.getHeight(); y++) {
                final int c = imgIn.getRed(x, y);
                if (c >= min && c < max) {
                    imgOut.setRGB(x, y, 0, 0, 0);
                } else {
                    imgOut.setRGB(x, y, 255, 255, 255);
                }
            }
        }

    }

}
