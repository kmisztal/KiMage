/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.use.threads;

import kimage.plugin.thread.SimplePluginOnThreads;
import kimage.plugins.color.Grayscale;
import kimage.plugins.color.SimpleInvert;
import kimage.plugins.convolve.Blur;
import kimage.plugins.thresholding.OtsuThreshold;
import kimage.tools.executors.Executor;
import kimage.tools.executors.gui.StepHandlerExecutor;

/**
 *
 * @author Krzysztof
 */
public class ThreadsTest {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filename = "./res/senna.jpg";
//        String filename = "res/test.png";
        
        Executor exec = new StepHandlerExecutor(filename).setFullscrean(false);
        
        exec.add(new SimplePluginOnThreads(new SimpleInvert()));

//        exec.add(new SimpleInvert());
//        exec.add(new OtsuThreshold());

//        exec.add(new Median(),
//                "size", 5);
//        
//        exec.add(new SimplePluginOnThreads(new Median()),
//                "size", 5);
        
        exec.execute();

        exec.save("./res/out.png");
    }
}