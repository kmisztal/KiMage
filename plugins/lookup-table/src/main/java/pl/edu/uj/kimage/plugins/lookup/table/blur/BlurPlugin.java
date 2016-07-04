package pl.edu.uj.kimage.plugins.lookup.table.blur;

import pl.edu.uj.kimage.plugin.PluginManifest;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugins.lookup.table.BlurFlowStep;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomaszkrzyzek on 15/06/16.
 */

public class BlurPlugin implements pl.edu.uj.kimage.plugin.Plugin {

    private static final String PLUGIN_NAME = "Blur";

    @Override
    public PluginManifest discover() {
        Set<Class<?>> inputTypes = new HashSet<>((Collection<Class<Image>>) Collections.singletonList(Image.class));
        Set<Class<?>> outputTypes = new HashSet<>((Collection<Class<Image>>) Collections.singletonList(Image.class));
        return new PluginManifest(PLUGIN_NAME, BlurFlowStep.class, new BlurStepFactory(), inputTypes, outputTypes);
    }
}
