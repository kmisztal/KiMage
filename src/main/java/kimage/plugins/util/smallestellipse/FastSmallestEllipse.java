package kimage.plugins.util.smallestellipse;

import kimage.image.Image;
import kimage.image.ImageType;
import kimage.plugin.Plugin;

/**
 * @author Krzysztof
 */
public class FastSmallestEllipse extends Plugin {

    @Override
    public void process(Image imgIn, Image imgOut) {
        checkImageType(imgIn, ImageType.BINARY);
    }

}
