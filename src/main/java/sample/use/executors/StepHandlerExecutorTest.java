package sample.use.executors;

import kimage.plugins.color.Grayscale;
import kimage.plugins.thresholding.OtsuThreshold;
import kimage.tools.executors.Executor;
import kimage.tools.executors.gui.StepHandlerExecutor;


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
