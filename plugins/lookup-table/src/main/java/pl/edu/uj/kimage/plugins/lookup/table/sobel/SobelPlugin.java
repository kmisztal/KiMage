package pl.edu.uj.kimage.plugins.lookup.table.sobel;

import pl.edu.uj.kimage.plugin.Plugin;
import pl.edu.uj.kimage.plugin.PluginManifest;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugins.lookup.table.SobelFlowStep;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SobelPlugin implements Plugin{
    private static final String PLUGIN_NAME = "Sobel";

    @Override
    public PluginManifest discover() {
        Set<Class<?>> inputTypes = new HashSet<>((Collection<Class<Image>>) Collections.singletonList(Image.class));
        Set<Class<?>> outputTypes = new HashSet<>((Collection<Class<Image>>) Collections.singletonList(Image.class));
        return new PluginManifest(PLUGIN_NAME, SobelFlowStep.class, new SobelStepFactory(), inputTypes, outputTypes);
    }
}
