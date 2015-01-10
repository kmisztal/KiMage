package kimage.plugin.simple;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.plugin.extras.Attributes;
import kimage.plugin.extras.Mask;

/**
 *
 * @author Krzysztof
 */
public abstract class SimplePlugin extends Plugin{
    
    @Override
    public abstract void process(Image imgIn, Image imgOut);

    @Override
    public void process(Image imgIn, Image imgOut, Attributes attrOut, Mask mask) {
        process(imgIn, imgOut);
    }
    
}
