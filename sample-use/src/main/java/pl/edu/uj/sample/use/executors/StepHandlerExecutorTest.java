package pl.edu.uj.sample.use.executors;

import pl.edu.uj.kimage.legacy.plugins.color.Grayscale;
import pl.edu.uj.kimage.legacy.plugins.thresholding.OtsuThreshold;
import pl.edu.uj.kimage.legacy.tools.executors.Executor;
import pl.edu.uj.kimage.legacy.tools.executors.gui.StepHandlerExecutor;


/**
 * @author Krzysztof
 */
public class StepHandlerExecutorTest {
    public static void main(String[] args) {
        String filename = "./res/senna.jpg";

        Executor exec = new StepHandlerExecutor(filename);

        exec.add(new Grayscale());

        exec.add(new OtsuThreshold());

        exec.execute();

        exec.save("./res/out.png");
    }
}
