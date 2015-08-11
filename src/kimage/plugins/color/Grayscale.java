package kimage.plugins.color;

import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;
import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.plugin.thread.ConcurrencyReady;

/**
 *
 * @author Krzysztof
 */
public class Grayscale extends Plugin implements ConcurrencyReady{

    @Override
    public void process(Image imgIn, Image imgOut) {
        new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null)
                .filter(imgIn.getBufferedImage(), imgOut.getBufferedImage()
                );
    }

    @Override
    public int getBoundaryForThreads() {
        return 0;
    }

}
