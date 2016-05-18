package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.ImageLoaded;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Image;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

public final class ImageProcessingFlow {
    public static final int START_STEP_ID = 0;
    private final FlowStepRepository flowStepRepository;
    private final EventBus eventBus;
    private final int flowLength;
    private final ExecutorService executorService;
    private final String taskId;

    ImageProcessingFlow(FlowStepRepository flowStepRepository, EventBus eventBus, String taskId, int flowLength, ExecutorService executorService) {
        this.flowStepRepository = flowStepRepository;
        this.eventBus = eventBus;
        this.flowLength = flowLength;
        this.executorService = executorService;
        this.taskId = taskId;
    }

    public void start(Image image) {
        registerFlowToEventBus();
        StepResultEvent stepResultEvent = new ImageLoaded(image);
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
        //TODO handle flow finished - should use taskId
    }

}
