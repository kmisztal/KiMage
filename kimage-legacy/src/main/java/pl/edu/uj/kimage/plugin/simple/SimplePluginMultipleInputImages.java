package pl.edu.uj.kimage.plugin.simple;

import pl.edu.uj.kimage.image.Image;
import pl.edu.uj.kimage.plugin.Plugin;

/**
 * @author Krzysztof
 */
public abstract class SimplePluginMultipleInputImages extends Plugin {

    public abstract void process(Image imgOut, Image... imgin);

}
