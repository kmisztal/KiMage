package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.ImageResultEvent;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Image;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

public final class ImageProcessingFlow {
    private final FlowStepRepository flowStepRepository;
    private final EventBus eventBus;
    private final int flowLength;
    private final ExecutorService executorService;

    ImageProcessingFlow(FlowStepRepository flowStepRepository, EventBus eventBus, int flowLength, ExecutorService executorService) {
        this.flowStepRepository = flowStepRepository;
        this.eventBus = eventBus;
        this.flowLength = flowLength;
        this.executorService = executorService;
    }

    public void start(Image image) {
        registerFlowToEventBus();
        StepResultEvent stepResultEvent = new ImageResultEvent(image);
        eventBus.publish(stepResultEvent);
    }

    private void process(StepResultEvent event) {
        Collection<FlowStep> notProcessedSteps = flowStepRepository.loadAll();
        notProcessedSteps.forEach(e -> executorService.submit(() -> e.process(event)));
    }

    private void registerFlowToEventBus() {
        eventBus.registerEventListener(StepResultEvent.class, (event -> {
            if (event.getStepNumber() == flowLength) {
                handleProcessingFinished(event);
            } else {
                process(event);
            }
        }));
    }

    private void handleProcessingFinished(StepResultEvent event) {
        //TODO handle flow finished
    }

}
