package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class ImageProcessorInfo {
    private final Set<PluginInfo> availablePlugins;


    public ImageProcessorInfo(@JsonProperty("availablePlugins") Set<PluginInfo> availablePlugins) {
        this.availablePlugins = availablePlugins;
    }

    public Set<PluginInfo> getAvailablePlugins() {
        return availablePlugins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageProcessorInfo that = (ImageProcessorInfo) o;

        return availablePlugins != null ? availablePlugins.equals(that.availablePlugins) : that.availablePlugins == null;

    }

    @Override
    public int hashCode() {
        return availablePlugins != null ? availablePlugins.hashCode() : 0;
    }
}
