package pl.edu.uj.kimage.tools.executors.gui;

import pl.edu.uj.kimage.image.Image;
import pl.edu.uj.kimage.plugin.Plugin;
import pl.edu.uj.kimage.tools.executors.Executor;
import pl.edu.uj.kimage.tools.executors.gui.helpers.StepHandlerExecutorGUI;
import pl.edu.uj.kimage.tools.executors.progressbar.ProcessingProgress;
import pl.edu.uj.kimage.utils.TimeExecution;


/**
 * @author Krzysztof
 */
public class StepHandlerExecutor extends Executor {
    private String title;
    private StepHandlerExecutorGUI imageList;
    private ProcessingProgress progress;
    private boolean fullscrean = false;

    public StepHandlerExecutor(String filename) {
        super(filename);
    }

    public StepHandlerExecutor(String filename, String title) {
        super(filename);
        this.title = title;
    }

    @Override
    public void executeCase() {
        if (imageList == null) {
            imageList = new StepHandlerExecutorGUI(title, fullscrean);
            progress = new ProcessingProgress(imageList, getPlugins().size());
            imageList.setVisible(true);
        }


        TimeExecution te = new TimeExecution();
        te.startEvent();

        imageList.addImage(currentImage.copy(), new Plugin() {

            @Override
            public void process(Image imgIn, Image imgOut) {
                //intentionally empty
            }

            @Override
            public String getInfo() {
                return "Original Image";
            }

            @Override
            public String getName() {
                return getInfo();
            }


        });

//        getPlugins().stream().forEach((p) -> {
        for (Plugin p : getPlugins()) {
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
//        });
        }

        te.stopEvent();
        te.printEventExecutionTime();
    }

    public StepHandlerExecutor setFullscrean(boolean fullscrean) {
        this.fullscrean = fullscrean;
        return this;
    }


}
