package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Task implements Serializable {
    private final ImageData imageData;
    private final String taskId;
    private final List<Step> processingSchedule;

    public Task(ImageData imageData, List<Step> processingSchedule) {
        this(UUID.randomUUID().toString(), imageData, processingSchedule);
    }

    @JsonCreator
    public Task(@JsonProperty("taskId") String taskId, @JsonProperty("imageData") ImageData imageData, @JsonProperty("processingSchedule") List<Step> processingSchedule) {
        this.taskId = taskId;
        this.imageData = imageData;
        this.processingSchedule = processingSchedule;
    }

    public ImageData getImageData() {
        return imageData;
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
        if (imageData != null ? !imageData.equals(task.imageData) : task.imageData != null) return false;
        return processingSchedule != null ? processingSchedule.equals(task.processingSchedule) : task.processingSchedule == null;

    }

    @Override
    public int hashCode() {
        int result = taskId != null ? taskId.hashCode() : 0;
        result = 31 * result + (imageData != null ? imageData.hashCode() : 0);
        result = 31 * result + (processingSchedule != null ? processingSchedule.hashCode() : 0);
        return result;
    }
}
