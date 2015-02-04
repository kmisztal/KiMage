package kimage.plugins.color;

import kimage.plugin.linear.LinearPlugin;
import static java.lang.Math.*;

/**
 *
 * @author Krzysztof
 */
public class SepiaI extends LinearPlugin {

    private int W;

    @Override
    protected void checkAttributes() {
        if (getAttribute("W") != null) {
            W = (int) getAttribute("W");
        } else {
            W = 30;
            setAttribute("W", W);
        }
    }

    @Override
    protected int colorChange(int rgb) {
        final int r = min((rgb >> 16 & 0xff) + 2 * W, 255);
        final int g = min((rgb >> 8 & 0xff) + W, 255);
        final int b = rgb & 0xff;
        return (rgb & 0xFF000000) | (r << 16) | (g << 8) | b;
    }
}
