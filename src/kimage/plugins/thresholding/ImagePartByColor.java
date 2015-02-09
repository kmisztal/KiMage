package kimage.plugins.thresholding;

import java.awt.Color;
import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public class ImagePartByColor extends Plugin {

    private Color color;

    @Override
    public void process(Image imgIn, Image imgOut) {
        if (getAttribute("color") != null) {
            color = (Color) getAttribute("color");
        } else {
            setAttribute("size", color);
        }
        
        final int col_v = color.getRGB();
        for (int x = 0; x < imgIn.getWidth(); x++) {
            for (int y = 0; y < imgIn.getHeight(); y++) {
                if (imgIn.getRGB(x, y) == col_v) {
                    imgOut.setRGB(x, y, 0, 0, 0);
                } else {
                    imgOut.setRGB(x, y, 255, 255, 255);
                }
            }
        }

    }

}
