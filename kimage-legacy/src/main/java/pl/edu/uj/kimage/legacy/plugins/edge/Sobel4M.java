package pl.edu.uj.kimage.legacy.plugins.edge;

import pl.edu.uj.kimage.legacy.plugin.edge.EdgeDetectionPlugin;

/**
 * @author Krzysztof
 */
public class Sobel4M extends EdgeDetectionPlugin {

    public static final float[] m1 = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
    public static final float[] m2 = {0, 1, 2, -1, 0, 1, -2, -1, 0};
    public static final float[] m3 = {1, 2, 1, 0, 0, 0, -1, -2, -1};
    public static final float[] m4 = {2, 1, 0, 1, 0, -1, 0, -1, -2};


    @Override
    public int G(int... x) {
        return (int) Math.sqrt(Math.pow(x[0], 2.) + Math.pow(x[1], 2.) + Math.pow(x[2], 2.) + Math.pow(x[3], 2.));
    }

    @Override
    public void setMasks() {
        masks = new float[][]{
                m1,
                m2,
                m3,
                m4
        };
    }

}