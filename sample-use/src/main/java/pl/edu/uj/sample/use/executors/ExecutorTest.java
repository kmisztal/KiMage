package pl.edu.uj.sample.use.executors;

import pl.edu.uj.kimage.plugins.color.Grayscale;
import pl.edu.uj.kimage.plugins.color.Invert;
import pl.edu.uj.kimage.plugins.color.SepiaI;
import pl.edu.uj.kimage.tools.executors.Executor;
import pl.edu.uj.kimage.tools.executors.quick.QuickExecutor;
import pl.edu.uj.kimage.utils.gui.ImageFrame;


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
