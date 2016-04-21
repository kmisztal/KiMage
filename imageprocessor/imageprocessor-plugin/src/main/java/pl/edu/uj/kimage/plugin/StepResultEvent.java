package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.eventbus.Event;

public abstract class StepResultEvent implements Event{
    private int stepId;

    public int getStepId() {
        return stepId;
    }

    void setFlowStepId(int stepId){
        this.stepId = stepId;
    }
}
