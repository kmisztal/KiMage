package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.PluginManifestRepository;
import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.Task;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.PluginManifest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageProcessingFlowFactory {

    public ImageProcessingFlow create(PluginManifestRepository manifestRepository, EventBus eventBus, Task task) {
        FlowStepRepository flowStepRepository = new FlowStepRepository();
        for (Step step : task.getProcessingSchedule()) {
            PluginManifest pluginManifest = manifestRepository.load(step.getPluginName());
            FlowStep flowStep = pluginManifest.getFlowStepFactory().create(step, eventBus);
            flowStepRepository.saveFlowStep(flowStep);
        }
        int flowLength = task.getProcessingSchedule().size();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);      //TODO read thread count per flow from properties
        return new ImageProcessingFlow(flowStepRepository, eventBus, task.getTaskId(), flowLength, fixedThreadPool);
    }
}
