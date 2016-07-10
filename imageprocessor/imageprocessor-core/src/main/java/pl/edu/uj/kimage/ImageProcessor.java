package pl.edu.uj.kimage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.Task;
import pl.edu.uj.kimage.eventbus.JsonMessageTranslator;
import pl.edu.uj.kimage.eventbus.VertXEventBus;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.processingFlow.ImageProcessingFlow;
import pl.edu.uj.kimage.processingFlow.ImageProcessingFlowFactory;
import pl.edu.uj.kimage.queueing.CalculationResult;
import pl.edu.uj.kimage.queueing.IncomingTaskHandler;
import pl.edu.uj.kimage.queueing.OutgoingTaskHandler;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ImageProcessor {
    private static final Logger logger = LogManager.getLogger(ImageProcessor.class);
    private static final JsonMessageTranslator MESSAGE_TRANSLATOR = new JsonMessageTranslator();
    private static final Consumer<Runnable> loggingConsumer = runnable -> {
        try {
            runnable.run();
        } catch (RuntimeException e) {
            logger.catching(e);
        }
    };
    private final IncomingTaskHandler incomingTaskHandler;
    private final OutgoingTaskHandler outgoingTaskHandler;
    private final PluginManifestRepository pluginManifestRepository;
    private int numberOfThreads = 2;        //todo read it from config
    private ExecutorService executorsPool;
    private ExecutorService outgoingTaskHandlerService;
    private volatile boolean running;

    public ImageProcessor(IncomingTaskHandler incomingTaskHandler, OutgoingTaskHandler outgoingTaskHandler, PluginManifestRepository pluginManifestRepository) {
        this.incomingTaskHandler = incomingTaskHandler;
        this.outgoingTaskHandler = outgoingTaskHandler;
        this.pluginManifestRepository = pluginManifestRepository;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    ImageProcessor.this.stop();
                } catch (InterruptedException e) {
                    logger.catching(e);
                }
            }
        });
    }

    public void start() {
        if (!running) {
            running = true;
            executorsPool = Executors.newFixedThreadPool(numberOfThreads);
            outgoingTaskHandlerService = Executors.newSingleThreadExecutor();
            outgoingTaskHandlerService.submit(() ->
                    loggingConsumer.accept(() -> outgoingTaskHandler.run())
            );
            loop();
        }
    }

    public void stop() throws InterruptedException {
        if (running) {
            running = false;
            executorsPool.shutdown();
            executorsPool.awaitTermination(3, TimeUnit.SECONDS);
            outgoingTaskHandler.stop();
        }
    }

    private void loop() {
        while (running) {
            Task task = takeTask();
            if (Objects.nonNull(task)) {
                int flowSize = task.getProcessingSchedule().size();
                VertXEventBus eventBus = new VertXEventBus(MESSAGE_TRANSLATOR);
                List<Step> processingSchedule = null;
                String taskId = task.getTaskId();
                ImageProcessingFlow imageProcessingFlow = new ImageProcessingFlowFactory().create(pluginManifestRepository, eventBus, flowSize, processingSchedule, taskId);
                Image image = null;
                executorsPool.submit(() -> loggingConsumer.accept(() -> imageProcessingFlow.start(image, (stepResultEvent) -> {
                    Object result = stepResultEvent.getResult();
                    CalculationResult calculationResult = new CalculationResult(taskId, result, result.getClass());
                    outgoingTaskHandler.addFinishedTask(calculationResult);
                })));
            }
        }
    }

    private Task takeTask() {
        Task task = null;
        try {
            task = incomingTaskHandler.takeTask();
        } catch (InterruptedException e) {
            logger.catching(e);
        }
        return task;
    }

}
