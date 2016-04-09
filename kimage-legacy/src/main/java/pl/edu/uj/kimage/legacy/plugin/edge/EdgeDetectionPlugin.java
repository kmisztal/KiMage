package pl.edu.uj.kimage.legacy.plugin.edge;

import pl.edu.uj.kimage.legacy.helpers.ColorHelper;
import pl.edu.uj.kimage.legacy.image.Image;
import pl.edu.uj.kimage.legacy.plugin.Plugin;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * @author Krzysztof
 */
public abstract class EdgeDetectionPlugin extends Plugin {

    protected float[][] masks;
    private BufferedImage[] im;

    public abstract void setMasks();

    public abstract int G(int... x);

    @Override
    public void process(Image imgIn, Image imgOut) {
        if (masks == null) {
            setMasks();
        }

        im = new BufferedImage[masks.length];
        int it = 0;
        for (float[] kernel : masks) {
            if (kernel.length != 9) {
                throw new RuntimeException("Not supported kernel size");
            }
            im[it++] = new ConvolveOp(
                    new Kernel(3, 3, kernel),
                    ConvolveOp.EDGE_ZERO_FILL,
                    null).filter(imgIn.getBufferedImage(), null);
        }

        for (int i = 0; i < imgIn.getWidth(); i++) {
            for (int j = 0; j < imgIn.getHeight(); j++) {
                final int result_red = G(ColorHelper.RED, im, i, j);
                final int result_green = G(ColorHelper.GREEN, im, i, j);
                final int result_blue = G(ColorHelper.BLUE, im, i, j);

                imgOut.setRGB(i, j, result_red, result_green, result_blue);
            }
        }
    }

    private int G(ColorHelper color, BufferedImage[] im, int i, int j) {
        int[] val = new int[im.length];
        for (int k = 0; k < val.length; k++) {
            val[k] = color.getColor(im[k].getRGB(i, j));
        }
        return ColorHelper.check(G(val));
    }

}
