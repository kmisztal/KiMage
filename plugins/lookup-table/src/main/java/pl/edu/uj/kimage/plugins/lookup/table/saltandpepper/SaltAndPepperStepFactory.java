package pl.edu.uj.kimage.plugins.lookup.table.saltandpepper;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStepFactory;
import pl.edu.uj.kimage.plugins.lookup.table.SaltAndPepperFlowStep;

/**
 * Created by tomaszkrzyzek on 15/06/16.
 */
public class SaltAndPepperStepFactory implements FlowStepFactory<SaltAndPepperFlowStep>{

    @Override
    public SaltAndPepperFlowStep create(Step step, EventBus eventBus) {
        return new SaltAndPepperFlowStep(step, eventBus);
    }
}
