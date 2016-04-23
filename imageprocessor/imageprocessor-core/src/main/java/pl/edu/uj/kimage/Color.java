package pl.edu.uj.kimage;

public final class Color {
    public static final int minColorValue = 0;
    public static final int maxColorValue = 255;

    public static final Color white = new Color(255, 255, 255);
    public static final Color black = new Color(0, 0, 0);

    private final int red, green, blue, alpha;

    public Color(final int gray) {
        this(gray, gray, gray, maxColorValue);
    }

    public Color(final int red, final int green, final int blue) {
        this(red, green, blue, maxColorValue);
    }

    public Color(final int red, final int green, final int blue, final int alpha) {
        this.red = clamp(red);
        this.green = clamp(green);
        this.blue = clamp(blue);
        this.alpha = clamp(alpha);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getAlpha() {
        return alpha;
    }

    private int clamp(final int value) {
        return Math.max(minColorValue, Math.min(value, maxColorValue));
    }
}
