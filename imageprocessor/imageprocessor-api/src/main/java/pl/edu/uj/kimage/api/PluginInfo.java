package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class PluginInfo {
    private final String name;
    private final Set<String> inputClassNames;
    private final Set<String> outputClassNames;

    public PluginInfo(@JsonProperty("name") String name, @JsonProperty("inputClassNames") Set<String> inputClassNames, Set<String> outputClassNames) {
        this.name = name;
        this.inputClassNames = inputClassNames;
        this.outputClassNames = outputClassNames;
    }

    public String getName() {
        return name;
    }

    public Set<String> getInputClassNames() {
        return inputClassNames;
    }

    public Set<String> getOutputClassNames() {
        return outputClassNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginInfo that = (PluginInfo) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (inputClassNames != null ? !inputClassNames.equals(that.inputClassNames) : that.inputClassNames != null)
            return false;
        return outputClassNames != null ? outputClassNames.equals(that.outputClassNames) : that.outputClassNames == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (inputClassNames != null ? inputClassNames.hashCode() : 0);
        result = 31 * result + (outputClassNames != null ? outputClassNames.hashCode() : 0);
        return result;
    }
}
