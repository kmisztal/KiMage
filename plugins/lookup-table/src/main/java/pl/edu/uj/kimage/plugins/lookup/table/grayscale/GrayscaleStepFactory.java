package pl.edu.uj.kimage.plugins.lookup.table.grayscale;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStepFactory;
import pl.edu.uj.kimage.plugins.lookup.table.GrayscaleFlowStep;

/**
 * Created by lnide on 6/2/2016.
 */
public class GrayscaleStepFactory implements FlowStepFactory<GrayscaleFlowStep> {
    @Override
    public GrayscaleFlowStep create(Step step, EventBus eventBus) {
        return new GrayscaleFlowStep(step, eventBus);
    }
}
