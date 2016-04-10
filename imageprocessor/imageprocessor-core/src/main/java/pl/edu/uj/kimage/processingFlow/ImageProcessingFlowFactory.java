package pl.edu.uj.kimage.processingFlow;

import pl.edu.uj.kimage.PluginManifestRepository;
import pl.edu.uj.kimage.api.ProcessingSchedule;
import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.PluginManifest;

public class ImageProcessingFlowFactory {

    public ImageProcessingFlow create(PluginManifestRepository manifestRepository, EventBus eventBus, ProcessingSchedule processingSchedule) {
        FlowStepRepository flowStepRepository = new FlowStepRepository();
        for (Step step : processingSchedule.getProcessingSchedule()) {
            PluginManifest pluginManifest = manifestRepository.load(step.getPluginName());
            FlowStep flowStep = pluginManifest.getFlowStepFactory().create(step, eventBus);
            flowStepRepository.saveFlowStep(flowStep);
        }
        int flowLength = processingSchedule.getProcessingSchedule().size();
        return new ImageProcessingFlow(flowStepRepository, eventBus, flowLength);
    }
}
