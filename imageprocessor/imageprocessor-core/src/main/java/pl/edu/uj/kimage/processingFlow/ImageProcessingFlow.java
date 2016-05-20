package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.ImageCalculated;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Image;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public final class ImageProcessingFlow {
    public static final int START_STEP_ID = 0;
    private final FlowStepRepository flowStepRepository;
    private final EventBus eventBus;
    private final int flowLength;
    private final ExecutorService executorService;
    private final String taskId;
    private Consumer<StepResultEvent> resultCollector;

    ImageProcessingFlow(FlowStepRepository flowStepRepository, EventBus eventBus, String taskId, int flowLength, ExecutorService executorService) {
        this.flowStepRepository = flowStepRepository;
        this.eventBus = eventBus;
        this.flowLength = flowLength;
        this.executorService = executorService;
        this.taskId = taskId;
    }

    public void start(Image image, Consumer<StepResultEvent> resultCollector) {
        this.resultCollector = resultCollector;
        registerFlowToEventBus();
        StepResultEvent stepResultEvent = new ImageCalculated(START_STEP_ID, image);
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
        resultCollector.accept(event);
    }
}
