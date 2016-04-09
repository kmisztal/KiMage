package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.Plugin;
import pl.edu.uj.kimage.eventbus.Event;

public class FlowStep {
    private final int stepId;
    private final Plugin stepRelatedPlugin;

    public FlowStep(int stepId, Plugin stepRelatedPlugin) {
        this.stepId = stepId;
        this.stepRelatedPlugin = stepRelatedPlugin;
    }

    public int getStepId() {
        return stepId;
    }

    public void processEvent(Event event)
    {
        stepRelatedPlugin.processEvent(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlowStep flowStep = (FlowStep) o;

        if (stepId != flowStep.stepId) return false;
        return stepRelatedPlugin != null ? stepRelatedPlugin.getClass().equals(flowStep.stepRelatedPlugin.getClass()) : flowStep.stepRelatedPlugin == null;

    }

    @Override
    public int hashCode() {
        int result = stepId;
        result = 31 * result + (stepRelatedPlugin != null ? stepRelatedPlugin.getClass().hashCode() : 0);
        return result;
    }
}
