package sample.use.plugins;

import kimage.image.BinaryImage;
import kimage.image.Image;
import kimage.plugins.color.Grayscale;
import kimage.plugins.thresholding.OtsuThreshold;
import kimage.plugins.util.smallestellipse.FastSmallestEllipse;
import kimage.tools.executors.Executor;
import kimage.tools.executors.gui.StepHandlerExecutor;

/**
 *
 * @author Krzysztof
 */
public class SmallestEllipseTest {
    public static void main(String[] args) {
//        BinaryImage image = BinaryImage.getImage("./res/circles.png");
//        
//        Executor exec = new StepHandlerExecutor(image);
//        
//        exec.add(new FastSmallestEllipse());
//        
////        exec.add(new OtsuThreshold());
//        
//        exec.execute();
//        
//        exec.save("./res/out.png");
    }
}
