package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Step  implements Serializable {
    private final int number;
    private final String pluginName;
    private final List<StepDependency> dependencies;

    @JsonCreator
    public Step(@JsonProperty("number") int number, @JsonProperty("pluginName") String pluginName, @JsonProperty("dependencies") List<StepDependency> dependencies) {
        this.number = number;
        this.pluginName = pluginName;
        this.dependencies = Collections.unmodifiableList(dependencies);
    }

    public int getNumber() {
        return number;
    }

    public String getPluginName() {
        return pluginName;
    }

    public List<StepDependency> getDependencies() {
        return dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Step step = (Step) o;

        if (number != step.number) return false;
        if (pluginName != null ? !pluginName.equals(step.pluginName) : step.pluginName != null) return false;
        return dependencies != null ? dependencies.equals(step.dependencies) : step.dependencies == null;

    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (pluginName != null ? pluginName.hashCode() : 0);
        result = 31 * result + (dependencies != null ? dependencies.hashCode() : 0);
        return result;
    }
}
