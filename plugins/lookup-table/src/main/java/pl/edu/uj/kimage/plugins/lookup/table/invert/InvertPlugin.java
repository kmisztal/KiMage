package pl.edu.uj.kimage.plugins.lookup.table.invert;

import pl.edu.uj.kimage.plugin.Plugin;
import pl.edu.uj.kimage.plugin.PluginManifest;
import pl.edu.uj.kimage.plugins.lookup.table.InvertFlowStep;

public class InvertPlugin implements Plugin {

    private static final String PLUGIN_NAME = "Invert";

    @Override
    public PluginManifest discover() {
        return new PluginManifest(PLUGIN_NAME, InvertFlowStep.class, new InvertStepFactory());
    }
}