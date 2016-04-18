package pl.edu.uj.kimage;

public final class Image {
    private final Color[] data;
    private final int width;
    private final int height;

    public Image() {
        width = height = 0;
        data = null;
    }

    public Image(final int width, final int height, final Color[] data) {
        if(data.length != width * height) {
            throw new IllegalArgumentException("Size of image does not match with input color data size.");
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

    public Color[] getData() {
        return data;
    }

    public Color getRGB(final int x, final int y) {
        isWithinDimensions(x, y);
        Color color = data[getPixelIndex(x, y)];
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Color getRGBA(final int x, final int y) {
        isWithinDimensions(x, y);
        Color color = data[getPixelIndex(x, y)];
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public int getRed(final int x, final int y) {
        return data[getPixelIndex(x, y)].getRed();
    }

    public int getGreen(final int x, final int y) {
        return data[getPixelIndex(x, y)].getGreen();
    }

    public int getBlue(final int x, final int y) {
        return data[getPixelIndex(x, y)].getBlue();
    }

    public int getAlpha(final int x, final int y) {
        return data[getPixelIndex(x, y)].getAlpha();
    }

    private int getPixelIndex(final int x, final int y) {
        return y * width + x;
    }

    private void isWithinDimensions(final int x, final int y) {
        if( ( x < 0 || x >= width ) || (y < 0 || y >= height) ) {
            throw new IllegalArgumentException("Wrong image pixel coordinates: " + x + " " + y);
        }
    }
}
