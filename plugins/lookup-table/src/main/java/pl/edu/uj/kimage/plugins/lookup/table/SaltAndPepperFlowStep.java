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
 * Created by tomaszkrzyzek on 15/06/16.
 */
public class SaltAndPepperFlowStep extends FlowStep {

    private static final double p = 0.2;

    public SaltAndPepperFlowStep(Step step, EventBus eventBus) {
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

                        if (Math.random() < p) {
                            int r;
                            if (Math.random() < 0.5) {
                                r = 255;
                            } else {
                                r = 0;
                            }

                            Color newColor = new Color(r, r, r);
                            builder.withColor(newColor, xCounter, yCounter);
                        }
                    }
                }

                Image newImage = builder.build();
                StepResultEvent result = new ImageCalculated(event.getStepNumber(), newImage);
                publish(result);
            }
        } catch (ClassNotFoundException e) {
            // TODO log
        }
    }
}
