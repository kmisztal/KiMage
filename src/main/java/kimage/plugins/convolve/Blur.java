package kimage.plugins.convolve;

import kimage.plugin.convolve.ConvolutionPlugin;
import kimage.plugin.extras.Attributes;

/**
 * Blurring is done for example by taking the average of the current pixel and it's 8 neighbors.
 *
 * @author Krzysztof
 */
public class Blur extends ConvolutionPlugin {

    @Override
    protected void createKernel() {
        kernel = new float[height * width];
        final float v = 1.f / kernel.length;

        for (int i = 0; i < kernel.length; ++i) {
            kernel[i] = v;
        }
    }

    @Override
    protected void setKernelSize() {
        if (getAttribute(Attributes.SIZE) != null) {
            height = width = (int) getAttribute(Attributes.SIZE);
        } else {
            height = width = 3;
        }
    }
}
