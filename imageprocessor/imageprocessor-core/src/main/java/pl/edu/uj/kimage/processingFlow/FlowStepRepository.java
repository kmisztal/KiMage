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

    public Collection<FlowStep> loadAll(){
        return flowSteps.values();
    }
}
