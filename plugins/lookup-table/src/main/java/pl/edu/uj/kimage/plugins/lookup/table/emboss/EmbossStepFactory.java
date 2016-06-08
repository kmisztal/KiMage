package pl.edu.uj.kimage.plugins.lookup.table.emboss;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStepFactory;
import pl.edu.uj.kimage.plugins.lookup.table.EmbossFlowStep;

public class EmbossStepFactory implements FlowStepFactory<EmbossFlowStep> {

    @Override
    public EmbossFlowStep create(Step step, EventBus eventBus) {
        return new EmbossFlowStep(step, eventBus);
    }
}