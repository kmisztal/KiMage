package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.StepResultEvent;

public class TestFlowStep extends FlowStep {

    public static final int RESULT = 120;

    public TestFlowStep(Step step, EventBus eventBus) {
        super(step, eventBus);
    }

    @Override
    public void processRelatedEvent(StepResultEvent event) {
        publish(new TestEvent(RESULT));
    }
}
