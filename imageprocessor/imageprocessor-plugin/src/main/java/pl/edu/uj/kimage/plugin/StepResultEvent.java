package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.eventbus.Event;

public class StepResultEvent<T> implements Event {
    private final int stepId;
    private final T result;

    public StepResultEvent(int stepId, T result) {
        this.stepId = stepId;
        this.result = result;
    }

    public int getStepId() {
        return stepId;
    }

    public Class<?> resultClass()
    {
        return result.getClass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StepResultEvent<?> that = (StepResultEvent<?>) o;

        if (stepId != that.stepId) return false;
        return result != null ? result.equals(that.result) : that.result == null;

    }

    @Override
    public int hashCode() {
        int result1 = stepId;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "StepResultEvent{" +
                "stepId=" + stepId +
                ", result=" + result +
                '}';
    }
}
