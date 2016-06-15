package pl.edu.uj.kimage.plugins.lookup.table;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.awt.ImageConvolution;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.ImageCalculated;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugin.model.ImageBuilder;

import java.awt.image.ConvolveOp;
import java.util.List;

public class SobelFlowStep extends FlowStep {

    private static final float[] GRADIENT_MASK_SOBEL_HORIZONTAL = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
    private static final float[] GRADIENT_MASK_SOBEL_VERTICAL = {-1, -2, -1, 0, 0, 0, 1, 2, 1};

    private static final int maskWidth = 3;
    private static final int maskHeight = 3;

    public SobelFlowStep(Step step, EventBus eventBus) {
        super(step, eventBus);
    }

    private static int G(int x, int y){
        return (int) Math.hypot(x,y);
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

                Image horizontal_mask_image = ImageConvolution.convolve(input, GRADIENT_MASK_SOBEL_HORIZONTAL, maskWidth, maskHeight);
                Image vertical_mask_image = ImageConvolution.convolve(input, GRADIENT_MASK_SOBEL_VERTICAL, maskWidth, maskHeight);

                int height = input.getHeight();
                int width = input.getWidth();

                ImageBuilder builder = new ImageBuilder(width, height);

                for (int i = 0; i < horizontal_mask_image.getWidth(); i++) {
                    for (int j = 0; j < horizontal_mask_image.getHeight(); j++) {
                        builder.withColor(getHypot(horizontal_mask_image.getColor(i,j), vertical_mask_image.getColor(i, j)), i, j);
                    }
                }

                StepResultEvent result = new ImageCalculated(event.getStepNumber(), builder.build());
                publish(result);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Color getHypot(Color x, Color y){
        final int newRed = clamp(G(x.getRed(),y.getRed()));
        final int newBlue = clamp(G(x.getBlue(),y.getBlue()));
        final int newGreen = clamp(G(x.getGreen(),y.getGreen()));
        final int newAlpha = clamp(G(x.getAlpha(),y.getAlpha()));

        return new Color(newRed, newGreen, newBlue, newAlpha);
    }

    private static int clamp(final int value) {
        return Math.max(0, Math.min(value, 255));
    }
}
