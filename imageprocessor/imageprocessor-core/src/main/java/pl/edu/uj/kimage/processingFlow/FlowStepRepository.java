package pl.edu.uj.kimage.processingFlow;

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

    public Collection<FlowStep> loadAllHigherThen(int stepId){
        return flowSteps.tailMap(stepId).values();
    }
}
