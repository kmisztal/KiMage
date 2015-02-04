package kimage.plugins.color;

import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;
import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public class Grayscale extends Plugin {

    @Override
    public void process(Image imgIn, Image imgOut) {
        new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null)
                .filter(imgIn.getBufferedImage(), imgOut.getBufferedImage()
                );
    }

}
