package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.uj.kimage.eventbus.Command;

/**
 * Command with task which is handled by image processor
 */
public class TaskCommand implements Command {
    private final Task task;

    public TaskCommand(@JsonProperty("task") Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
