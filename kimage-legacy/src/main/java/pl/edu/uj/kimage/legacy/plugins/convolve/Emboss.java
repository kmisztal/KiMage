package pl.edu.uj.kimage.legacy.plugins.convolve;

import pl.edu.uj.kimage.legacy.plugin.convolve.ConvolutionPlugin;


/**
 * @author Krzysztof
 */
public class Emboss extends ConvolutionPlugin {

    @Override
    protected void createKernel() {
        kernel = new float[]{
                -2.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 2.0f
        };
    }

    @Override
    protected void setKernelSize() {
        width = height = 3;
    }

}

