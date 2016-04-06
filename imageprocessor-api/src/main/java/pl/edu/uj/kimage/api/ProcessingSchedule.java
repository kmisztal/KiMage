package pl.edu.uj.kimage.api;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ProcessingSchedule implements Serializable {
    private final List<Step> processingSchedule;

    public ProcessingSchedule(List<Step> processingSchedule) {
        this.processingSchedule = Collections.unmodifiableList(processingSchedule);
    }

    public List<Step> getProcessingSchedule() {
        return processingSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessingSchedule that = (ProcessingSchedule) o;

        return processingSchedule != null ? processingSchedule.equals(that.processingSchedule) : that.processingSchedule == null;

    }

    @Override
    public int hashCode() {
        return processingSchedule != null ? processingSchedule.hashCode() : 0;
    }
}
