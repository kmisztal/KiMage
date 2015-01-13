package kimage.plugins.noise;

import kimage.helpers.ColorHelper;
import kimage.image.Image;
import kimage.plugin.simple.SimplePlugin;

/**
 *
 * @author Krzysztof
 */
public class Gaussian extends SimplePlugin {

    private double p = 0.1;
    private double sigma = 10;

    @Override
    public void process(Image imgIn, Image imgOut) {
        if (getAttribute("p") != null) {
            p = (Double) getAttribute("p");
        } else {
            setAttribute("p", p);
        }

        if (getAttribute("sigma") != null) {
            sigma = (Double) getAttribute("sigma");
        } else {
            setAttribute("sigma", sigma);
        }
        
        final int width = imgIn.getWidth();
        final int height = imgIn.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (Math.random() < p) {
                    final double u1 = Math.random();
                    final double u2 = Math.random();
                    final int v = (int) (sigma * Math.sqrt(-2. * Math.log(u1)) * Math.cos(2. * Math.PI * u2));
                    imgOut.setRGB(x, y,
                            ColorHelper.check(imgIn.getRed(x, y) + v),
                            ColorHelper.check(imgIn.getGreen(x, y) + v),
                            ColorHelper.check(imgIn.getBlue(x, y) + v));
                }
            }
        }
    }

}
