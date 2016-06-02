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

/**
 * Created by lnide on 6/2/2016.
 */
public class GrayscaleFlowStep extends FlowStep {
    public GrayscaleFlowStep(Step step, EventBus eventBus) {
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
                        Color color = input.getColor(xCounter, yCounter);

                        int grayscale = (int) (new Double(color.getRed()) * 0.2126 +
                                new Double(color.getGreen()) * 0.7152 +
                                new Double(color.getBlue()) * 0.0722);

                        Color grayscaleColor = new Color(grayscale, grayscale, grayscale);
                        builder.withColor(grayscaleColor, xCounter, yCounter);
                    }
                }

                Image grayscaleImage = builder.build();
                StepResultEvent result = new ImageCalculated(event.getStepNumber(), grayscaleImage);
                publish(result);
            }
        } catch (ClassNotFoundException e) {
            // TODO log
        }

    }
}
