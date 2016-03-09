package kimage.plugins.noise;

import kimage.helpers.ColorHelper;
import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 * @author Krzysztof
 */
public class Uniform extends Plugin {

    private double p = 0.1;
    private double a = 0;
    private double b = 1;

    @Override
    public void process(Image imgIn, Image imgOut) {
        if (getAttribute("p") != null) {
            p = (Double) getAttribute("p");
        } else {
            setAttribute("p", p);
        }

        if (getAttribute("a") != null) {
            a = (Double) getAttribute("a");
        } else {
            setAttribute("a", a);
        }

        if (getAttribute("b") != null) {
            b = (Double) getAttribute("b");
        } else {
            setAttribute("b", b);
        }

        for (int y = 0; y < imgIn.getHeight(); y++) {
            for (int x = 0; x < imgIn.getWidth(); x++) {
                if (Math.random() < p) {
                    final int v = (int) (Math.random() * (b - a) + a);
                    imgOut.setRGB(x, y,
                            ColorHelper.check(imgIn.getRed(x, y) + v),
                            ColorHelper.check(imgIn.getGreen(x, y) + v),
                            ColorHelper.check(imgIn.getBlue(x, y) + v));
                }
            }
        }
    }

}
