package pl.edu.uj.kimage.plugins.lookup.table.noise.gaussian;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStepFactory;
import pl.edu.uj.kimage.plugins.lookup.table.GaussianNoiseFlowStep;

public class GaussianNoiseStepFactory implements FlowStepFactory<GaussianNoiseFlowStep> {

    @Override
    public GaussianNoiseFlowStep create(Step step, EventBus eventBus) {
        return new GaussianNoiseFlowStep(step, eventBus);
    }
}