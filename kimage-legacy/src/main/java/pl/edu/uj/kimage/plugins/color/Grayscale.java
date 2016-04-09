package pl.edu.uj.kimage.plugins.color;

import pl.edu.uj.kimage.image.Image;
import pl.edu.uj.kimage.plugin.Plugin;
import pl.edu.uj.kimage.plugin.thread.ConcurrencyReady;

import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;

/**
 * @author Krzysztof
 */
public class Grayscale extends Plugin implements ConcurrencyReady {

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
