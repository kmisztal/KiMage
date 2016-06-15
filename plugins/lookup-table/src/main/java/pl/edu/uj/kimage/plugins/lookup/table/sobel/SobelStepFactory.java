package pl.edu.uj.kimage.plugins.lookup.table.sobel;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStepFactory;
import pl.edu.uj.kimage.plugins.lookup.table.SobelFlowStep;

public class SobelStepFactory implements FlowStepFactory<SobelFlowStep> {
    @Override
    public SobelFlowStep create(Step step, EventBus eventBus) {
        return new SobelFlowStep(step, eventBus);
    }
}
