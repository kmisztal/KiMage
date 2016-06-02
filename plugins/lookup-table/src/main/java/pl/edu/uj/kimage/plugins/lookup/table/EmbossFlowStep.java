package pl.edu.uj.kimage.plugins.lookup.table;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.awt.ImageConverter;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.ImageCalculated;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Image;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.List;

public class EmbossFlowStep extends FlowStep {

    public EmbossFlowStep(Step step, EventBus eventBus) {
        super(step, eventBus);
    }

    private static final float[] kernel = new float[]{
            -2.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 2.0f
    };

    private static int kernelWidth = 3;
    private static int kernelHeight = 3;

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

                int height = input.getHeight();
                int width = input.getWidth();

                ImageConverter imageConverter = new ImageConverter();

                BufferedImage inputBufferedImage = imageConverter.toBufferedImage(input);
                BufferedImage outputBufferedImage = new BufferedImage(width, height, inputBufferedImage.getType());

                final BufferedImageOp bio
                        = new ConvolveOp(new Kernel(kernelWidth, kernelHeight, kernel));
                try {
                    bio.filter(inputBufferedImage, outputBufferedImage);
                } catch (java.lang.IllegalArgumentException ex) {
                }

                Image embossedImage = imageConverter.toImage(outputBufferedImage);
                StepResultEvent result = new ImageCalculated(event.getStepNumber(), embossedImage);
                publish(result);
            }
        } catch (ClassNotFoundException e) {
            // TODO log
        }
    }
}