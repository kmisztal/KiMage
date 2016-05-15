package pl.edu.uj.kimage.api;

public class ImageDataBuilder {
    private final int width;
    private final int height;
    private final byte[] data;

    public ImageDataBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        data = new byte[width * height * 4];
    }

    public ImageDataBuilder withPoint(int x, int y, byte red, byte green, byte blue, byte alpha) {
        int startIndex = (width * y + x) * 4;
        data[startIndex] = red;
        data[startIndex + 1] = green;
        data[startIndex + 2] = blue;
        data[startIndex + 3] = alpha;
        return this;
    }

    public ImageData build() {
        return new ImageData(width, height, data);
    }

}
