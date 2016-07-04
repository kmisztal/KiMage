package pl.edu.uj.kimage.plugins.lookup.table.invert;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStepFactory;
import pl.edu.uj.kimage.plugins.lookup.table.InvertFlowStep;

public class InvertStepFactory implements FlowStepFactory<InvertFlowStep> {

    @Override
    public InvertFlowStep create(Step step, EventBus eventBus) {
        return new InvertFlowStep(step, eventBus);
    }
}