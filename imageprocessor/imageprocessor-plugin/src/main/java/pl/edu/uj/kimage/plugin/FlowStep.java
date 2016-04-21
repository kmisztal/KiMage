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

    /**
     * called by flow processor in order to pass occurring event
     * @param event flow event
     */

    public abstract void process(StepResultEvent event);

    /**
     * Used by plugin in order to publish calculation results to event bus
     *
     * @param stepResultEvent event plugin wants to publish
     */
    protected void publish(StepResultEvent stepResultEvent){
        stepResultEvent.setFlowStepId(step.getNumber());
        eventBus.publish(stepResultEvent);
    }
}
