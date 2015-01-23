package kimage.tools.executors;

import kimage.image.Image;


/**
 * executor in place - input is the same as output for each plugin
 *
 * @author Krzysztof
 */
public class QuickExecutor extends Executor {

    public QuickExecutor(String filename) {
        super(filename);
    }

    public QuickExecutor(Image img) {
        super(img);
    }

    @Override
    public void executeCase() {
        getPlugins().stream().forEach((p) -> {
            p.process(currentImage);
        });
    }

}
