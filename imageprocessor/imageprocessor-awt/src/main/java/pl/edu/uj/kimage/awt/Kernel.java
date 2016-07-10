package pl.edu.uj.kimage.awt;

public class Kernel {
    private final int width;
    private final int height;
    private final float[] kernel;

    public Kernel(int width, int height, float[] kernel) {
        this.width = width;
        this.height = height;
        this.kernel = kernel;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float[] getKernel() {
        return kernel;
    }
}
