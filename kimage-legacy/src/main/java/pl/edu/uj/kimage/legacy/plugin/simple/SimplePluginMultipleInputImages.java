package pl.edu.uj.kimage.legacy.plugin.simple;

import pl.edu.uj.kimage.legacy.image.Image;
import pl.edu.uj.kimage.legacy.plugin.Plugin;

/**
 * @author Krzysztof
 */
public abstract class SimplePluginMultipleInputImages extends Plugin {

    public abstract void process(Image imgOut, Image... imgin);

}
