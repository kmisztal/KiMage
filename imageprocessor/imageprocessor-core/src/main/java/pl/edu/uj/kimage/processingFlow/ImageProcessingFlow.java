package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.Image;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowEvent;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.StepResultEvent;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

public final class ImageProcessingFlow {
    public static final int START_STEP_ID = 0;
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
        StepResultEvent<Image> integerStepResultEvent = new StepResultEvent<>(START_STEP_ID, image);
        eventBus.publish(integerStepResultEvent);
    }

    private void process(FlowEvent event) {
        Collection<FlowStep> notProcessedSteps = flowStepRepository.loadAll();
        notProcessedSteps.forEach(e -> executorService.submit(() -> e.process(event)));
    }

    private void registerFlowToEventBus() {
        eventBus.registerEventListener(StepResultEvent.class, (event -> {
            if (event.getStepId() == flowLength) {
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
