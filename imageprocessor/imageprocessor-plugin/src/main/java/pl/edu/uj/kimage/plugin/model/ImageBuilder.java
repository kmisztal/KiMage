package pl.edu.uj.kimage.plugin.model;

// TODO add @NotThreadSafe after merge configuration module
public class ImageBuilder {
    private static final int MIN_IMAGE_COORDINATE = 0;
    private static final int ZERO = 0;
    private final int width;
    private final int height;
    private final Color[] data;

    public ImageBuilder(int width, int height) {
        validateMinCoordinates(width, height);
        this.width = width;
        this.height = height;
        this.data = new Color[width * height];
    }

    public ImageBuilder withColor(Color color, int x, int y) {
        validateCoordinates(x, y);
        int row = width * y;
        int index = row + x;
        data[index] = color;
        return this;
    }

    public Image build() {
        for (int i = 0, length = data.length; i < length; i++) {
            if (data[i] == null) {
                int y = i / width;
                int x = i % width;
                throw new IllegalStateException(String.format("Null color at x=%s, y=%s", x, y));
            }
        }
        return new Image(width, height, data);
    }

    private void validateCoordinates(final int x, final int y) {
        if ((x < MIN_IMAGE_COORDINATE || x >= width) || (y < MIN_IMAGE_COORDINATE || y >= height)) {
            throw new IllegalArgumentException(String.format("Wrong pixel coordinates x=%s, y=%s. Image width: %s, " +
                    "image height: %s", x, y, width, height));
        }
    }

    private void validateMinCoordinates(int width, int height) {
        if (width == ZERO) {
            throw new IllegalArgumentException("Width cannot be 0");
        }
        if (height == ZERO) {
            throw new IllegalArgumentException("Height cannot be 0");
        }
    }
}