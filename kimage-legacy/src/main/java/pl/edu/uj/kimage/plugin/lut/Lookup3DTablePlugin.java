package pl.edu.uj.kimage.plugin.lut;

import pl.edu.uj.kimage.image.Image;
import pl.edu.uj.kimage.plugin.Plugin;

import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;

/**
 * Lookup Table filter with different table for each channel
 *
 * @author Krzysztof
 */
public abstract class Lookup3DTablePlugin extends Plugin {

    protected short[] red = new short[256];
    protected short[] green = new short[256];
    protected short[] blue = new short[256];

    /**
     * method should fill arrays: red, green, blue
     */
    protected abstract void createLUT();

    @Override
    public void process(Image imgIn, Image imgOut) {
        createLUT();
        short[][] LUT = new short[][]{
                red, green, blue
        };

        final BufferedImageOp bio
                = new LookupOp(new ShortLookupTable(0, LUT), null);
        bio.filter(imgIn.getBufferedImage(), imgOut.getBufferedImage());
    }

}
