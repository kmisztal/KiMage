package pl.edu.uj.kimage.plugins.lookup.table;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.ImageCalculated;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugin.model.ImageBuilder;

import java.util.List;

public class SepiaIIFlowStep extends FlowStep {
    public SepiaIIFlowStep(Step step, EventBus eventBus) {
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

                int height = input.getHeight();
                int width = input.getWidth();

                ImageBuilder builder = new ImageBuilder(width, height);
                for (int yCounter = 0; yCounter < height; yCounter++) {
                    for (int xCounter = 0; xCounter < width; xCounter++) {
                        final int red = input.getColor(xCounter, yCounter).getRed();
                        final int green = input.getColor(xCounter, yCounter).getGreen();
                        final int blue = input.getColor(xCounter, yCounter).getBlue();

                        Color sepiaColor = new Color(
                                Math.min((int) (0.393 * red + 0.769 * green + 0.189 * blue), 255),
                                Math.min((int) (0.349 * red + 0.686 * green + 0.168 * blue), 255),
                                Math.min((int) (0.272 * red + 0.534 * green + 0.131 * blue), 255),
                                input.getColor(xCounter, yCounter).getAlpha());
                        builder.withColor(sepiaColor, xCounter, yCounter);
                    }
                }

                Image sepiaImage = builder.build();
                StepResultEvent result = new ImageCalculated(event.getStepNumber(), sepiaImage);
                publish(result);

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
