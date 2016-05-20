package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.eventbus.EventBus;

import java.util.List;

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
     * Should contains plugin specific logic
     *
     * @param event flow event
     */
    public abstract void processRelatedEvent(StepResultEvent event);

    /**
     * called by flow processor in order to pass occurring event
     *
     * @param event flow event
     */
    public void process(StepResultEvent event) {

        if (event instanceof ImageCalculated) {
            Step step = getStep();
            List<StepDependency> dependencies = step.getDependencies();

            for (StepDependency dependency : dependencies) {
                int dependentStepNumber = dependency.getDependentStepNumber();
                int eventStepNumber = event.getStepNumber();
                if (dependentStepNumber == eventStepNumber) {
                    processRelatedEvent(event);
                }
            }
            // TODO log some information about dependencies: size etc.
        }
    }

    /**
     * Used by plugin in order to publish calculation results to event bus
     *
     * @param stepResultEvent event plugin wants to publish
     */
    protected void publish(StepResultEvent stepResultEvent) {
        stepResultEvent.setFlowStepNumber(step.getNumber());
        eventBus.publish(stepResultEvent);
    }

    public Step getStep() {
        return step;
    }
}