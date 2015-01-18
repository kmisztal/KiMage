package kimage.plugin.simple;

import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public abstract class SimplePluginMultipleInputImages extends Plugin{
    
    public abstract void process(Image imgOut, Image ... imgin); 
    
}
