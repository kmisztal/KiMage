package pl.edu.uj.kimage;

import pl.edu.uj.kimage.plugin.PluginManifest;

import java.util.HashMap;
import java.util.Map;

public class PluginManifestRepository {
    private final Map<String, PluginManifest> plugins = new HashMap<>();

    public void save(PluginManifest pluginManifest) {
        plugins.put(pluginManifest.getName(), pluginManifest);
    }

    public PluginManifest load(String pluginName) {
        return plugins.get(pluginName);
    }
}
