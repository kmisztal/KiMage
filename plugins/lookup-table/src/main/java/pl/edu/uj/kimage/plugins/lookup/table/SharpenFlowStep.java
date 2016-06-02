package pl.edu.uj.kimage.plugins.lookup.table;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.awt.ImageConverter;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.ImageCalculated;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugin.model.ImageBuilder;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.List;

public class SharpenFlowStep extends FlowStep {

    public SharpenFlowStep(Step step, EventBus eventBus) {
        super(step, eventBus);
    }

    private static final float[] kernel = new float[]{
            -1.0f, -1.0f, -1.0f,
            -1.0f, 9.0f, -1.0f,
            -1.0f, -1.0f, -1.0f
    };

    private static final int kernelWidth = 3;
    private static final int kernelHeight = 3;

    private static final ImageConverter imageConverter = new ImageConverter();

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

                final int height = input.getHeight();
                final int width = input.getWidth();

                BufferedImage inputBufferedImage = imageConverter.toBufferedImage(input);
                BufferedImage outputBufferedImage = new BufferedImage(width, height, inputBufferedImage.getType());

                final BufferedImageOp bio = new ConvolveOp(new Kernel(kernelWidth, kernelHeight, kernel));

                try {
                    bio.filter(inputBufferedImage, outputBufferedImage);
                } catch (java.lang.IllegalArgumentException ex) {
                    ex.printStackTrace();
                }

                Image sharpenedImage = imageConverter.toImage(outputBufferedImage);
                StepResultEvent result = new ImageCalculated(event.getStepNumber(), sharpenedImage);
                publish(result);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}