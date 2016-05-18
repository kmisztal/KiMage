package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.plugin.StepResultEvent;

public class TestEvent extends StepResultEvent<Integer> {
    public TestEvent(int result) {
        super(result);
    }
}
