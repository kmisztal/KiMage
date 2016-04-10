package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.eventbus.Event;

public abstract class FlowEvent implements Event{
    protected final int stepId;

    public FlowEvent(int stepId) {
        this.stepId = stepId;
    }

    public int getStepId() {
        return stepId;
    }
}
