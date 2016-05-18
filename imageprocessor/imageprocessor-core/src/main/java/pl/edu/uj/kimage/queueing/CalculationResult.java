package pl.edu.uj.kimage.queueing;

public class CalculationResult<T> {
    private final String taskId;
    private final T result;
    private final Class resultClass;

    public CalculationResult(String taskId, T result, Class<T> resultClass) {
        this.taskId = taskId;
        this.result = result;
        this.resultClass = resultClass;
    }

    public String getTaskId() {
        return taskId;
    }

    public Object getResult() {
        return result;
    }

    public Class getResultClass() {
        return resultClass;
    }
}
