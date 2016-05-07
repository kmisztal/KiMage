package pl.edu.uj.kimage.queueing;

public class CalculationResult {
    private final String taskId;
    private final Object result;
    private final Class resultClass;

    public <T>CalculationResult(String taskId, T result, Class<T> resultClass) {
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
