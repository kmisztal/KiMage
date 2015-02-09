package test.executors;

import kimage.plugins.convolve.Blur;
import kimage.tools.executors.Executor;
import kimage.tools.executors.gui.StepHandlerExecutor;


/**
 *
 * @author Krzysztof
 */
public class StepHandlerExecutorTest {
    public static void main(String[] args) {
        String filename = "./res/lena.png";
        
        Executor exec = new StepHandlerExecutor(filename);
        
//        exec.add(new Invert());
//        exec.add(new Invert());
//        
//        exec.add(new Blur(),
//                "size", 6);
        
        exec.add(new Blur());
        
        exec.execute();
        
        exec.save("./res/out.png");
    }
}
