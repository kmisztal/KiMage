package pl.edu.uj.kimage.legacy.plugins.util.smallestellipse;

import pl.edu.uj.kimage.legacy.image.Image;
import pl.edu.uj.kimage.legacy.image.ImageType;
import pl.edu.uj.kimage.legacy.plugin.Plugin;

/**
 * @author Krzysztof
 */
public class FastSmallestEllipse extends Plugin {

    @Override
    public void process(Image imgIn, Image imgOut) {
        checkImageType(imgIn, ImageType.BINARY);
    }

}
