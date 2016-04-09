package pl.edu.uj.kimage.legacy.helpers;

import java.awt.*;
import java.util.Random;

/**
 * @author Krzysztof
 */
public class ColorTable {
    public static Color[] randomColorArray(int size) {
        Random r = new Random();
        Color[] ret = new Color[size];
        for (int i = 0; i < size; ++i) {
            ret[i] = Color.getHSBColor(r.nextFloat(),//random hue, color
                    1.0f,//full saturation, 1.0 for 'colorful' colors, 0.0 for grey
                    1.0f //1.0 for bright, 0.0 for black
            );
        }
        return ret;
    }
}
