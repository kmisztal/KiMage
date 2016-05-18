package pl.edu.uj.kimage.api;

public class ProcessingResultEvent<T> {
    private final Class<T> resultType;
    private final T data;

    public ProcessingResultEvent(Class<T> resultType, T data) {
        this.resultType = resultType;
        this.data = data;
    }

    public Class<T> getResultType() {
        return resultType;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ProcessingResultEvent{" +
                "resultType=" + resultType +
                ", data=" + data +
                '}';
    }
}
