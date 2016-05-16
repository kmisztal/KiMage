package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.eventbus.Event;

public abstract class StepResultEvent<T> implements Event{
    private int stepId;
    private final T result;

    public StepResultEvent(T result) {
        this.result = result;
    }

    public int getStepId() {
        return stepId;
    }

    void setFlowStepId(int stepId){
        this.stepId = stepId;
    }

    public T getResult() {
        return result;
    }
}
