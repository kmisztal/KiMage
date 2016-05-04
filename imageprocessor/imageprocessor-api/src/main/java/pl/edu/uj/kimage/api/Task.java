package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Task implements Serializable {
    private final String taskId;
    private final byte[] data;
    private final List<Step> processingSchedule;

    @JsonCreator
    public Task(@JsonProperty("taskId") String taskId, @JsonProperty("data") byte[] data, @JsonProperty("processingSchedule") List<Step> processingSchedule) {
        this.taskId = taskId;
        this.data = data;
        this.processingSchedule = processingSchedule;
    }

    public byte[] getData() {
        return data;
    }

    public List<Step> getProcessingSchedule() {
        return processingSchedule;
    }

    public String getTaskId() {
        return taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (taskId != null ? !taskId.equals(task.taskId) : task.taskId != null) return false;
        if (!Arrays.equals(data, task.data)) return false;
        return processingSchedule != null ? processingSchedule.equals(task.processingSchedule) : task.processingSchedule == null;

    }

    @Override
    public int hashCode() {
        int result = taskId != null ? taskId.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(data);
        result = 31 * result + (processingSchedule != null ? processingSchedule.hashCode() : 0);
        return result;
    }
}
