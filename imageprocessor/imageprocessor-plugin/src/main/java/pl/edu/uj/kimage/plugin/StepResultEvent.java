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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StepResultEvent event = (StepResultEvent) o;

        if (stepNumber != event.stepNumber) return false;
        return result != null ? !result.equals(event.result) : event.result != null;
    }

    @Override
    public int hashCode() {
        int result = stepNumber;
        result = 31 * result + (this.result != null ? this.result.hashCode() : 0);
        return result;
    }

}