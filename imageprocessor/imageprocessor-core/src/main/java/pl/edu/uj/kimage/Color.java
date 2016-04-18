package pl.edu.uj.kimage;

public final class Color {
    private final int red, green, blue, alpha;

    public static final int minColorValue = 0;
    public static final int maxColorValue = 255;

    public Color(final int red, final int green, final int blue) {
        this(red, green, blue, maxColorValue);
    }

    public Color(final int red, final int green, final int blue, final int alpha) {
        this.red   = clamp(red);
        this.green = clamp(green);
        this.blue  = clamp(blue);
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
