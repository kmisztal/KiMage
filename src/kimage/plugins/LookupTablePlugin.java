package kimage.plugins;

import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import kimage.image.Image;
import kimage.plugins.Plugin;
import kimage.utils.Attributes;
import kimage.utils.Mask;

/**
 *
 * @author Krzysztof
 */
public abstract class LookupTablePlugin extends Plugin {

    protected short[] LUT;

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
