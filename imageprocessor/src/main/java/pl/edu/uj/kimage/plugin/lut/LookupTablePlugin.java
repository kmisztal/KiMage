package pl.edu.uj.kimage.plugin.lut;

import pl.edu.uj.kimage.image.Image;
import pl.edu.uj.kimage.plugin.Plugin;

import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;

/**
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
