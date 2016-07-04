package pl.edu.uj.kimage.plugins.lookup.table.sharpen;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStepFactory;
import pl.edu.uj.kimage.plugins.lookup.table.SharpenFlowStep;

public class SharpenStepFactory implements FlowStepFactory<SharpenFlowStep> {

    @Override
    public SharpenFlowStep create(Step step, EventBus eventBus) {
        return new SharpenFlowStep(step, eventBus);
    }


}
