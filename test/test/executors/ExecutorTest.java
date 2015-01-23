package test.executors;

import kimage.plugins.color.Invert;
import kimage.plugins.color.SepiaI;
import kimage.tools.executors.Executor;
import kimage.tools.executors.QuickExecutor;



/**
 *
 * @author Krzysztof
 */
public class ExecutorTest {
    public static void main(String[] args) {
        String filename = "./res/lena.png";
        
        Executor exec = new QuickExecutor(filename);        
        
        exec.add(new Invert());
        
        exec.add(new SepiaI(),
                "W", 40);
        
        exec.execute();
        
        exec.save("./res/out.png");
    }
}
