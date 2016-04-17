package pl.edu.uj.kimage;

//only for RGBA
public class Image {
    private int[] data;
    final private int width;
    final private int height;

    public Image() {
        width = height = 0;
        data = null;
    }

    public Image(final int width, final int height) {
        this.width = width;
        this.height = height;
        data = new int[width * height * 4];
        setVisible();
    }

    private void setVisible() {
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                setAlpha(i, j, 255);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getRGBA(final int x, final int y) {
        checkCoordinates(x, y);
        return new int[]{getRed(x, y), getGreen(x, y), getBlue(x, y), getAlpha(x, y)};
    }

    public void setRGBA(final int x, final int y, final int r, final int g, final int b, final int a) {
        checkCoordinates(x, y);
        setRed(x, y, r);
        setGreen(x, y, g);
        setBlue(x, y, b);
        setAlpha(x, y, a);
    }

    public int[] getRGB(final int x, final int y) {
        checkCoordinates(x, y);
        return new int[]{getRed(x, y), getGreen(x, y), getBlue(x, y)};
    }

    public void setRGB(final int x, final int y, final int r, final int g, final int b) {
        checkCoordinates(x, y);
        setRed(x, y, r);
        setGreen(x, y, g);
        setBlue(x, y, b);
    }

    public int getRed(final int x, final int y) {
        return data[getPixelBeginning(x, y)];
    }

    public int getGreen(final int x, final int y) {
        return data[getPixelBeginning(x, y) + 1];
    }

    public int getBlue(final int x, final int y) {
        return data[getPixelBeginning(x, y) + 2];
    }

    public int getAlpha(final int x, final int y) {
        return data[getPixelBeginning(x, y) + 3];
    }

    public void setRed(final int x, final int y, final int value) {
        data[getPixelBeginning(x, y)] = Math.max(0, Math.min(value, 255));
    }

    public void setGreen(final int x, final int y, final int value) {
        data[getPixelBeginning(x, y) + 1] = Math.max(0, Math.min(value, 255));
    }

    public void setBlue(final int x, final int y, final int value) {
        data[getPixelBeginning(x, y) + 2] = Math.max(0, Math.min(value, 255));
    }

    public void setAlpha(final int x, final int y, final int value) {
        data[getPixelBeginning(x, y) + 3] = Math.max(0, Math.min(value, 255));
    }

    private void checkCoordinates(final int x, final int y) {
        if( ( x < 0 || x >= width ) || (y < 0 || y >= height) ) {
            throw new RuntimeException("Wrong image pixel coordinates: " + x + " " + y);
        }
    }

    private int getPixelBeginning(final int x, final int y) {
        return width * y * 4 + x * 4;
    }
}
