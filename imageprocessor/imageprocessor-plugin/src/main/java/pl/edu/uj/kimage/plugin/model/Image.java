package pl.edu.uj.kimage.plugin.model;

// TODO add @Immutable after merge configuration module
public final class Image {
    private final Color[] data;
    private final int width;
    private final int height;

    Image(final int width, final int height, final Color[] data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor(final int x, final int y) {
        isWithinDimensions(x, y);
        int index = y * width + x;
        return data[index];
    }

    public Color[] getData(){
        return this.data;
    }

    private void isWithinDimensions(final int x, final int y) {
        if ((x < 0 || x >= width) || (y < 0 || y >= height)) {
            throw new IllegalArgumentException("Wrong image pixel coordinates. " +
                    "X should be between " + 0 + " and " + width + ". Y should be between " + 0 + " and " + height);
        }
    }
}
