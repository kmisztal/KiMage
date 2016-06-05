package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.eventbus.Event;

public abstract class StepResultEvent<T> implements Event{
    private final T result;
    private int stepNumber;

    protected StepResultEvent(T result) {
        this.result = result;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    void setFlowStepNumber(int stepId) {
        this.stepNumber = stepId;
    }

    public T getResult() {
        return result;
    }
}
