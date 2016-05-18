package pl.edu.uj.kimage.plugin;

import java.util.Set;

public class PluginManifest {
    private final String name;
    private final Class<? extends FlowStep> stepType;
    private final FlowStepFactory flowStepFactory;
    private final Set<Class<?>> requiredInputTypes;
    private final Set<Class<?>> producedOutputTypes;

    public PluginManifest(String name, Class<? extends FlowStep> stepType, FlowStepFactory flowStepFactory, Set<Class<?>> requiredInputTypes, Set<Class<?>> producedOutputTypes) {
        this.name = name;
        this.stepType = stepType;
        this.flowStepFactory = flowStepFactory;
        this.requiredInputTypes = requiredInputTypes;
        this.producedOutputTypes = producedOutputTypes;
    }

    public String getName() {
        return name;
    }

    public Class<? extends FlowStep> getStepType() {
        return stepType;
    }

    public FlowStepFactory getFlowStepFactory() {
        return flowStepFactory;
    }

    public Set<Class<?>> getRequiredInputTypes() {
        return requiredInputTypes;
    }

    public Set<Class<?>> getProducedOutputTypes() {
        return producedOutputTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginManifest that = (PluginManifest) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (stepType != null ? !stepType.equals(that.stepType) : that.stepType != null) return false;
        if (flowStepFactory != null ? !flowStepFactory.equals(that.flowStepFactory) : that.flowStepFactory != null)
            return false;
        if (requiredInputTypes != null ? !requiredInputTypes.equals(that.requiredInputTypes) : that.requiredInputTypes != null)
            return false;
        return producedOutputTypes != null ? producedOutputTypes.equals(that.producedOutputTypes) : that.producedOutputTypes == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (stepType != null ? stepType.hashCode() : 0);
        result = 31 * result + (flowStepFactory != null ? flowStepFactory.hashCode() : 0);
        result = 31 * result + (requiredInputTypes != null ? requiredInputTypes.hashCode() : 0);
        result = 31 * result + (producedOutputTypes != null ? producedOutputTypes.hashCode() : 0);
        return result;
    }
}
