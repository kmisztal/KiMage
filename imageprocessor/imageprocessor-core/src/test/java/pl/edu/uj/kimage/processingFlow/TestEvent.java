package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.plugin.StepResultEvent;

public class TestEvent extends StepResultEvent {
    private final int result;

    public TestEvent(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }
}
