package kimage.plugins.convolve;

import kimage.plugin.convolve.ConvolutionPlugin;


/**
 *
 * @author Krzysztof
 */
public class Emboss extends ConvolutionPlugin {

    @Override
    protected void createKernel() {
        kernel = new float[]{
            -1.0f, -1.0f,  0.0f,
            -1.0f,  0.0f,  1.0f,
             0.0f,  1.0f,  1.0f
        };
    }

    @Override
    protected void setKernelSize() {
        width = height = 3;
    }

}

