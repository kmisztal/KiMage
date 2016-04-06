package pl.edu.uj.kimage.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Task  implements Serializable {
    private final byte[] data;
    private final List<Step> processingSchedule;

    public Task(byte[] data, List<Step> processingSchedule) {
        this.data = data;
        this.processingSchedule = processingSchedule;
    }

    public byte[] getData() {
        return data;
    }

    public List<Step> getProcessingSchedule() {
        return processingSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!Arrays.equals(data, task.data)) return false;
        return processingSchedule != null ? processingSchedule.equals(task.processingSchedule) : task.processingSchedule == null;

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(data);
        result = 31 * result + (processingSchedule != null ? processingSchedule.hashCode() : 0);
        return result;
    }
}
