package kimage.plugin.convolve;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public abstract class ConvolutionPlugin extends Plugin {

    protected int width;
    protected int height;
    protected float[] kernel;

    protected abstract void createKernel();

    protected abstract void setKernelSize();

    @Override
    public void process(Image imgIn, Image imgOut) {

        setKernelSize();
        
        createKernel();

        final BufferedImageOp bio
                = new ConvolveOp(new Kernel(width, height, kernel));
        try{
            bio.filter(imgIn.getBufferedImage(), imgOut.getBufferedImage());
        }catch(java.lang.IllegalArgumentException ex){
            BufferedImage im = imgIn.getCopyOfBufferedImage();
            bio.filter(imgIn.getBufferedImage(), im);
            imgIn.setBufferedImage(im);
        }
    }

}
