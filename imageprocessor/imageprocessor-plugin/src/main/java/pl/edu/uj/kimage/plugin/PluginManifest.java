package pl.edu.uj.kimage.plugin;

public class PluginManifest {
    private final String name;
    private final Class<? extends FlowStep> stepType;
    private final FlowStepFactory flowStepFactory;

    public PluginManifest(String name, Class<? extends FlowStep> stepType, FlowStepFactory flowStepFactory) {
        this.name = name;
        this.stepType = stepType;
        this.flowStepFactory = flowStepFactory;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginManifest that = (PluginManifest) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (stepType != null ? !stepType.equals(that.stepType) : that.stepType != null) return false;
        return flowStepFactory != null ? flowStepFactory.equals(that.flowStepFactory) : that.flowStepFactory == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (stepType != null ? stepType.hashCode() : 0);
        result = 31 * result + (flowStepFactory != null ? flowStepFactory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PluginManifest{" +
                "name='" + name + '\'' +
                ", stepType=" + stepType +
                ", flowStepFactory=" + flowStepFactory +
                '}';
    }
}
