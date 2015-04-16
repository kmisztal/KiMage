package kimage.helpers;

import kimage.image.BinaryImage;
import kimage.plugins.util.smallestellipse.FastSmallestEllipse;
import kimage.tools.executors.Executor;
import kimage.tools.executors.gui.StepHandlerExecutor;

/**
 *
 * @author Krzysztof
 */
public class TestRun {
    public static void main(String[] args) {
        BinaryImage image = BinaryImage.getImage("./res/circles.png");
        
        Executor exec = new StepHandlerExecutor(image);
        
        exec.add(new FastSmallestEllipse());
        
//        exec.add(new OtsuThreshold());
        
        exec.execute();
        
        exec.save("./res/out.png");
    }
}
