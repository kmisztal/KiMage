package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;

public abstract class FlowStep {
    private final Step step;
    private final EventBus eventBus;

    public FlowStep(Step step, EventBus eventBus) {
        this.step = step;
        this.eventBus = eventBus;
    }

    public int getStepId() {
        return step.getNumber();
    }

    protected EventBus getEventBus() {
        return eventBus;
    }

    public abstract void process(FlowEvent event);
}
