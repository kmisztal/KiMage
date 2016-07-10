package pl.edu.uj.kimage.plugins.lookup.table.sepiaII;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStepFactory;
import pl.edu.uj.kimage.plugins.lookup.table.SepiaIIFlowStep;

public class SepiaIIStepFactory implements FlowStepFactory<SepiaIIFlowStep> {
    @Override
    public SepiaIIFlowStep create(Step step, EventBus eventBus) {
        return new SepiaIIFlowStep(step, eventBus);
    }
}
