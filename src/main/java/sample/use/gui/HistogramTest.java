package sample.use.gui;

import java.awt.EventQueue;
import kimage.image.Image;
import kimage.utils.histogram.gui.HistogramGUI;

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
