package pl.edu.uj.kimage.plugin.model;

public final class Color {
    public static final int MIN_COLOR_CHANNEL_VALUE = 0;
    public static final int MAX_COLOR_CHANNEL_VALUE = 255;

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);

    private final int red, green, blue, alpha;

    public Color(final int gray) {
        this(gray, gray, gray, MAX_COLOR_CHANNEL_VALUE);
    }

    public Color(final int red, final int green, final int blue) {
        this(red, green, blue, MAX_COLOR_CHANNEL_VALUE);
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
        return Math.max(MIN_COLOR_CHANNEL_VALUE, Math.min(value, MAX_COLOR_CHANNEL_VALUE));
    }
}
