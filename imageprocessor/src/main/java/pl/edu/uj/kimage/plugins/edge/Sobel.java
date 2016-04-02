package pl.edu.uj.kimage.plugins.edge;

import pl.edu.uj.kimage.plugin.edge.EdgeDetectionPlugin;

/**
 * @author Krzysztof
 */
public class Sobel extends EdgeDetectionPlugin {

    public static final float[] GRADIENT_MASK_SOBEL_HORIZONTAL = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
    public static final float[] GRADIENT_MASK_SOBEL_VERTICAL = {-1, -2, -1, 0, 0, 0, 1, 2, 1};


    @Override
    public int G(int... x) {
        return (int) Math.hypot(x[0], x[1]);
    }

    @Override
    public void setMasks() {
        masks = new float[][]{
                GRADIENT_MASK_SOBEL_HORIZONTAL,
                GRADIENT_MASK_SOBEL_VERTICAL
        };
    }

}
