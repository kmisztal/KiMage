package pl.edu.uj.kimage.plugin.model;

import java.util.Objects;

public final class Image {
    private final Color[] data;
    private final int width;
    private final int height;

    public Image(final int width, final int height, final Color[] data) {
        Objects.requireNonNull(data, "Color data cannot be null");

        if (data.length != width * height) {
            throw new IllegalArgumentException("Size of image does not match with input color data size. " +
                    "Expected = " + data.length + " Actual = " + width * height);
        }

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
        return data[getPixelIndex(x, y)];
    }

    private int getPixelIndex(final int x, final int y) {
        return y * width + x;
    }

    private void isWithinDimensions(final int x, final int y) {
        if ((x < 0 || x >= width) || (y < 0 || y >= height)) {
            throw new IllegalArgumentException("Wrong image pixel coordinates. " +
                    "X should be between " + 0 + " and " + width + ". Y should be between " + 0 + " and " + height);
        }
    }
}
