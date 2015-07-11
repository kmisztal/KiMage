package kimage.tools.executors.gui;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.tools.executors.Executor;
import kimage.tools.executors.gui.helpers.StepHandlerExecutorGUI;
import kimage.tools.executors.gui.helpers.TimeExecution;
import kimage.tools.executors.progressbar.ProcessingProgress;


/**
 *
 * @author Krzysztof
 */
public class StepHandlerExecutor extends Executor {
    private String title;
    private StepHandlerExecutorGUI imageList;
    private ProcessingProgress progress;

    public StepHandlerExecutor(String filename) {
        super(filename);
    }
    
    public StepHandlerExecutor(Image image) {
        super(image);
    }

    public StepHandlerExecutor(String filename, String title) {
        super(filename);
        this.title = title;
    }

    public StepHandlerExecutor(Image image, String title) {
        super(image);
        this.title = title;
    }
    
    @Override
    public void executeCase() {
        if(imageList == null){
            imageList = new StepHandlerExecutorGUI(title);
            progress = new ProcessingProgress(imageList, getPlugins().size());
            imageList.setVisible(true);
        }
        

        TimeExecution te = new TimeExecution();
        te.startEvent();
//        getPlugins().stream().forEach((p) -> {
        for(Plugin p : getPlugins()){
            te.startJob(p.getName());
            
            p.process(currentImage);
            imageList.addImage(currentImage.copy(), p);
            progress.increment();

//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(StepHandlerExecutor.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            te.endJob(true);                     
        }
        //);
        
        te.stopEvent();
        te.printEventExecutionTime();
    }
    
    public void addImage(Image image){
        imageList.addImage(image, null);
    }

}
