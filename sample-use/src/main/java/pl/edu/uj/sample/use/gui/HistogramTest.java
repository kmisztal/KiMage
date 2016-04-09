package pl.edu.uj.sample.use.gui;

import pl.edu.uj.kimage.legacy.image.Image;
import pl.edu.uj.kimage.legacy.utils.histogram.gui.HistogramGUI;

import java.awt.*;

/**
 * @author Krzysztof
 */
public class HistogramTest {

    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            new HistogramGUI(new Image("./res/apples.png"), false);
//        });


        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new HistogramGUI(new Image("./res/apples.png"), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
