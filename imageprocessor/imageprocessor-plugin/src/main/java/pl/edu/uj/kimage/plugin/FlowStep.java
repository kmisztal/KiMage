package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.eventbus.Event;
import pl.edu.uj.kimage.eventbus.EventBus;

public abstract class FlowStep {
    private final int stepId;
    private final EventBus eventBus;

    public FlowStep(int stepId, EventBus eventBus) {
        this.stepId = stepId;
        this.eventBus = eventBus;
    }

    public int getStepId() {
        return stepId;
    }

    protected EventBus getEventBus() {
        return eventBus;
    }

    public abstract void processEvent(Event event);
}
