package kimage.plugins.convolve;

import kimage.plugin.convolve.ConvolutionPlugin;


/**
 * To sharpen the image is very similar to finding edges, add the original
 * image, and the image after the edge detection to each other, and the result
 * will be a new image where the edges are enhanced, making it look sharper.
 * Adding those two images is done by taking the edge detection filter from the
 * previous example, and incrementing the center value of it with 1. Now the sum
 * of the filter elements is 1 and the result will be an image with the same
 * brightness as the original, but sharper.
 *
 * @author Krzysztof
 */
public class Sharpen extends ConvolutionPlugin {

    @Override
    protected void createKernel() {
        kernel = new float[]{
                -1.0f, -1.0f, -1.0f,
                -1.0f, 9.0f, -1.0f,
                -1.0f, -1.0f, -1.0f
        };
    }

    @Override
    protected void setKernelSize() {
        width = height = 3;
    }

}
