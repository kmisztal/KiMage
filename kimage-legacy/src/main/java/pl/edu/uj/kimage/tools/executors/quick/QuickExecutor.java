package pl.edu.uj.kimage.tools.executors.quick;

import pl.edu.uj.kimage.image.Image;
import pl.edu.uj.kimage.plugin.Plugin;
import pl.edu.uj.kimage.tools.executors.Executor;


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
//        getPlugins().stream().forEach((p) -> {
//           p.process(currentImage); 
//        });

        for (Plugin p : getPlugins()) {
            p.process(currentImage);
        }
    }

}
