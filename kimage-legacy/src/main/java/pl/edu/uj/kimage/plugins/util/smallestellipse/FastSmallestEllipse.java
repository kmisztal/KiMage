package pl.edu.uj.kimage.plugins.util.smallestellipse;

import pl.edu.uj.kimage.image.Image;
import pl.edu.uj.kimage.image.ImageType;
import pl.edu.uj.kimage.plugin.Plugin;

/**
 * @author Krzysztof
 */
public class FastSmallestEllipse extends Plugin {

    @Override
    public void process(Image imgIn, Image imgOut) {
        checkImageType(imgIn, ImageType.BINARY);
    }

}
