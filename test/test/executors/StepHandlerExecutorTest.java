package test.executors;

import kimage.plugins.color.Invert;
import kimage.plugins.convolve.Blur;
import kimage.tools.executors.Executor;
import kimage.tools.executors.gui.StepHandlerExecutor;


/**
 *
 * @author Krzysztof
 */
public class StepHandlerExecutorTest {
    public static void main(String[] args) {
        String filename = "./res/senna.jpg";
        
        Executor exec = new StepHandlerExecutor(filename);
        
        exec.add(new Invert());
        exec.add(new Invert());
        
        exec.add(new Blur(),
                "size", 6);
        
        exec.execute();
        
        exec.save("./res/out.png");
    }
}
