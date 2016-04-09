package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.eventbus.Event;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageProcessingFlow {
    private final FlowStepRepository flowStepRepository;
    private AtomicInteger currentStep;

    //TODO add processing mechanism

    public ImageProcessingFlow(FlowStepRepository flowStepRepository) {
        this.flowStepRepository = flowStepRepository;
        this.currentStep = new AtomicInteger(0);
    }

    public void processEvent(Event event){
        Collection<FlowStep> notProcessedSteps = flowStepRepository.loadAllHigherThen(currentStep.get());
        notProcessedSteps.forEach(e->e.processEvent(event));
    }

}
