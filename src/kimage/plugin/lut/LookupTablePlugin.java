package kimage.plugin.lut;

import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public abstract class LookupTablePlugin extends Plugin {

    protected short[] LUT;

    protected abstract void createLUT();

    @Override
    public void process(Image imgIn, Image imgOut) {

        createLUT();

        final BufferedImageOp bio
                = new LookupOp(new ShortLookupTable(0, LUT), null);
        bio.filter(imgIn.getBufferedImage(), imgOut.getBufferedImage());
    }

}
