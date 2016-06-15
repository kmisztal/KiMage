package pl.edu.uj.kimage.plugins.lookup.table.blur;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugins.lookup.table.BlurFlowStep;

/**
 * Created by tomaszkrzyzek on 15/06/16.
 */
public class BlurStepFactory implements pl.edu.uj.kimage.plugin.FlowStepFactory {
    @Override
    public FlowStep create(Step step, EventBus eventBus) {
        return new BlurFlowStep(step, eventBus);
    }
}
