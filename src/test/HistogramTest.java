package test;

import kimage.image.Image;
import kimage.utils.gui.Histogram;

/**
 *
 * @author Krzysztof
 */
public class HistogramTest {
    public static void main(String[] args) {
        new Histogram(new Image("./res/apples.png"));
    }
}
