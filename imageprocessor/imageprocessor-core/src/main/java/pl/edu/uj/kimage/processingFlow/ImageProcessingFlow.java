package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.Image;
import pl.edu.uj.kimage.eventbus.Event;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageProcessingFlow {
    private final FlowStepRepository flowStepRepository;
    private AtomicInteger currentStep;

    //TODO add processing mechanism, event bus

    public ImageProcessingFlow(FlowStepRepository flowStepRepository) {
        this.flowStepRepository = flowStepRepository;
        this.currentStep = new AtomicInteger(0);
    }

    public void processEvent(Event event){
        Collection<FlowStep> notProcessedSteps = flowStepRepository.loadAllLaterThen(currentStep.get());
        notProcessedSteps.forEach(e->e.processEvent(event));
    }

    public void start(Image image)
    {
        StepResultEvent<Integer> integerStepResultEvent = new StepResultEvent<>(0, 1);

    }

}
