package pl.edu.uj.kimage.plugins.lookup.table;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.ImageCalculated;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugin.model.ImageBuilder;

import java.util.List;

/**
 * Created by tomaszkrzyzek on 15/06/16.
 */
public class BlurFlowStep extends pl.edu.uj.kimage.plugin.FlowStep {

    private static final int kernelWidth = 3;
    private static final int kernelHeight = 3;

    private static final int kernelSize = kernelHeight * kernelWidth;

    private static final float[] kernel = new float[]{
                        1.f/kernelSize, 1.f/kernelSize, 1.f/kernelSize,
                        1.f/kernelSize, 1.f/kernelSize, 1.f/kernelSize,
                        1.f/kernelSize, 1.f/kernelSize, 1.f/kernelSize
    };

    public BlurFlowStep(Step step, EventBus eventBus) {
        super(step, eventBus);
    }

    @Override
    public void processRelatedEvent(StepResultEvent event) {
        List<StepDependency> dependencies = getStep().getDependencies();
        StepDependency stepDependency = dependencies.get(0);
        String eventTypeName = stepDependency.getEventTypeName();

        try {
            Class<?> clazz = Class.forName(eventTypeName);
            if (clazz.isInstance(ImageCalculated.class)) {

                ImageCalculated imageCalculated = (ImageCalculated) event;
                Image input = imageCalculated.getLoadedImage();

                Image blurredImage = ImageConvolution.convolve(input, kernel, kernelWidth, kernelHeight);

                StepResultEvent result = new ImageCalculated(event.getStepNumber(), blurredImage);
                publish(result);
            }
        } catch (ClassNotFoundException e) {
            // TODO log
        }
    }
}
