package kimage.tools.executors.gui;

import kimage.plugin.Plugin;
import kimage.tools.executors.Executor;
import kimage.tools.executors.progressbar.ProcessingProgress;


/**
 *
 * @author Krzysztof
 */
public class StepHandlerExecutorWithProgressBar extends Executor {

    public StepHandlerExecutorWithProgressBar(String filename) {
        super(filename);
    }

    @Override
    public void executeCase() {
        ProcessingProgress progress = new ProcessingProgress(null, getPlugins().size());

//        getPlugins().stream().forEach((p) -> {
        for(Plugin p : getPlugins()){
            p.process(currentImage);
            progress.increment();            
        }
        //);
    }

}
