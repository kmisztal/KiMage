package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.Event;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowEvent;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.StepResultEvent;

public class TestFlowStep extends FlowStep {

    public static final int TEST_RESULT = 0;

    public TestFlowStep(Step step, EventBus eventBus) {
        super(step, eventBus);
    }

    @Override
    public void processEvent(FlowEvent event) {
        getEventBus().publish(new StepResultEvent<>(event.getStepId()+1, TEST_RESULT));
    }
}
