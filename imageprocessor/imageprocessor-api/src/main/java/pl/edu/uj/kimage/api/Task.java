package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {
    private final ImageData imageData;
    private final List<Step> processingSchedule;

    @JsonCreator
    public Task(@JsonProperty("imageData") ImageData imageData, @JsonProperty("processingSchedule") List<Step> processingSchedule) {
        this.imageData = imageData;
        this.processingSchedule = processingSchedule;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public List<Step> getProcessingSchedule() {
        return processingSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (imageData != null ? !imageData.equals(task.imageData) : task.imageData != null) return false;
        return processingSchedule != null ? processingSchedule.equals(task.processingSchedule) : task.processingSchedule == null;

    }

    @Override
    public int hashCode() {
        int result = imageData != null ? imageData.hashCode() : 0;
        result = 31 * result + (processingSchedule != null ? processingSchedule.hashCode() : 0);
        return result;
    }
}
