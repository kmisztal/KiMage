package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStepFactory;

public class TestFlowStepFactory implements FlowStepFactory<TestFlowStep> {
    @Override
    public TestFlowStep create(Step step, EventBus eventBus) {
        return new TestFlowStep(step, eventBus);
    }
}
