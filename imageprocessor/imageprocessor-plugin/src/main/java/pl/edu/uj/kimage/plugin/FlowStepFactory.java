package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;

public interface FlowStepFactory<T extends FlowStep> {
    T create(Step step, EventBus eventBus);
}
