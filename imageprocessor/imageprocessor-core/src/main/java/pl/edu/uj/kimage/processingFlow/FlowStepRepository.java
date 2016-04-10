package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.plugin.FlowStep;

import java.util.Collection;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class FlowStepRepository {
    private final SortedMap<Integer, FlowStep> flowSteps = new ConcurrentSkipListMap<>();

    public void saveFlowStep(FlowStep flowStep) {
        flowSteps.put(flowStep.getStepId(), flowStep);
    }

    public FlowStep load(int stepId) {
        return flowSteps.get(stepId);
    }

    public Collection<FlowStep> loadAllLaterThen(int stepId){
        return flowSteps.tailMap(stepId).values();
    }

    public Collection<FlowStep> loadAll(){
        return flowSteps.values();
    }
}
