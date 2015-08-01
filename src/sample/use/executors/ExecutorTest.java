package sample.use.executors;

import kimage.plugins.color.Grayscale;
import kimage.plugins.color.Invert;
import kimage.plugins.color.SepiaI;
import kimage.plugins.edge.Sobel;
import kimage.plugins.edge.Sobel4M;
import kimage.tools.executors.Executor;
import kimage.tools.executors.quick.QuickExecutor;
import kimage.utils.gui.ImageFrame;
import sun.awt.image.ImageFormatException;



/**
 *
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
