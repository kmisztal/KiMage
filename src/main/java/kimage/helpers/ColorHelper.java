package kimage.helpers;

/**
 * @author Krzysztof
 */
public enum ColorHelper {

    RED(0), GREEN(1), BLUE(2);
    private final int value;

    ColorHelper(int v) {
        value = v;
    }

    public static int getSize() {
        return 3;
    }

    public static int check(int val) {
        if (val < 0) {
            return 0;
        }
        if (val > 255) {
            return 255;
        }
        return val;
    }

    public int get() {
        return value;
    }

    public int getColor(int val) {
        switch (this) {
            case RED:
                return (val & 0x00FF0000) >>> 16;
            case GREEN:
                return (val & 0x0000FF00) >>> 8;
            case BLUE:
                return (val & 0x000000FF);
        }
        throw new RuntimeException("Wrong using ");
    }

    /**
     * Limits the color value between 0 and 255.
     *
     * @param color - color to check
     * @return int - the color value
     */
    public int limit8bitsColor(int color) {
        if (color > 255) {
            color = 255;
            return (color);
        }

        if (color < 0) {
            color = 0;
            return (color);
        }
        return color;
    }

}
