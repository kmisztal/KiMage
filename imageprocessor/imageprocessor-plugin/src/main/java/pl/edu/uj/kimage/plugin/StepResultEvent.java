package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.eventbus.Event;

public abstract class StepResultEvent implements Event{
    private int stepNumber;

    public int getStepNumber() {
        return stepNumber;
    }

    void setFlowStepNumber(int stepId) {
        this.stepNumber = stepId;
    }
}
