package pl.edu.uj.kimage.plugin;

public class StepResultEvent<T> extends FlowEvent {
    private final T result;

    public StepResultEvent(int stepId, T result) {
        super(stepId);
        this.result = result;
    }

    public Class<?> resultClass() {
        return result.getClass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StepResultEvent<?> that = (StepResultEvent<?>) o;

        return result != null ? result.equals(that.result) : that.result == null;

    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StepResultEvent{" +
                "result=" + result +
                '}';
    }
}
