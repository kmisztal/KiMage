package kimage.plugins;

import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import kimage.image.Image;
import kimage.utils.Attributes;
import kimage.utils.Mask;

/**
 * Lookup Table filter with different table for each channel
 * @author Krzysztof
 */
public abstract class Lookup3DTablePlugin extends Plugin {

    protected short[][] LUT;

    protected abstract void createLUT();

    @Override
    public void process(Image imgIn, Image imgOut, Attributes attrOut, Mask mask) {
        if (mask != null) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        createLUT();
        
        final BufferedImageOp bio
                = new LookupOp(new ShortLookupTable(0, LUT), null);
        bio.filter(imgIn.getBufferedImage(), imgOut.getBufferedImage());
    }

}
