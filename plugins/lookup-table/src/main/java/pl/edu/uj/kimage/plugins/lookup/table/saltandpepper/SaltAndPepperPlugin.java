package pl.edu.uj.kimage.plugins.lookup.table.saltandpepper;

import pl.edu.uj.kimage.plugin.Plugin;
import pl.edu.uj.kimage.plugin.PluginManifest;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugins.lookup.table.SaltAndPepperFlowStep;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by tomaszkrzyzek on 15/06/16.
 */
public class SaltAndPepperPlugin implements Plugin{

    private static final String PLUGIN_NAME = "SaltAndPepper";

    @Override
    public PluginManifest discover() {
        Set<Class<?>> inputTypes = new HashSet<>((Collection<Class<Image>>) Collections.singletonList(Image.class));
        Set<Class<?>> outputTypes = new HashSet<>((Collection<Class<Image>>) Collections.singletonList(Image.class));
        return new PluginManifest(PLUGIN_NAME, SaltAndPepperFlowStep.class, new SaltAndPepperStepFactory(), inputTypes, outputTypes);
    }
}
