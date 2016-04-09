package pl.edu.uj.sample.use.executors;

import pl.edu.uj.kimage.legacy.plugins.color.Grayscale;
import pl.edu.uj.kimage.legacy.plugins.color.Invert;
import pl.edu.uj.kimage.legacy.plugins.color.SepiaI;
import pl.edu.uj.kimage.legacy.tools.executors.Executor;
import pl.edu.uj.kimage.legacy.tools.executors.quick.QuickExecutor;
import pl.edu.uj.kimage.legacy.utils.gui.ImageFrame;


/**
 * @author Krzysztof
 */
public class ExecutorTest {
    public static void main(String[] args) {
        String filename = "./res/lena.png";

        Executor exec = new QuickExecutor(filename);

        exec.add(new Grayscale());

        exec.add(new SepiaI(),
                "W", 40);

        exec.add(new Invert());

        exec.execute();

        new ImageFrame(exec.getResultImage()).display();

        exec.save("./res/out.png");
    }
}
