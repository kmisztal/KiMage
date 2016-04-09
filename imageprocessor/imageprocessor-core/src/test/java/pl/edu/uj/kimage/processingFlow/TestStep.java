package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.eventbus.Event;
import pl.edu.uj.kimage.eventbus.EventBus;

public class TestStep extends FlowStep {

    public TestStep(int stepId, EventBus eventBus) {
        super(stepId, eventBus);
    }

    @Override
    public void processEvent(Event event) {
        //TODO this is not finished
    }
}
