package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.PluginManifestRepository;
import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.Task;
import pl.edu.uj.kimage.configuration.PropertiesReader;
import pl.edu.uj.kimage.configuration.PropertyName;
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
        int nThreads = PropertiesReader.readInt(PropertyName.NUMBER_OF_THREADS);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(nThreads);
        return new ImageProcessingFlow(flowStepRepository, eventBus, flowLength, fixedThreadPool);
    }
}