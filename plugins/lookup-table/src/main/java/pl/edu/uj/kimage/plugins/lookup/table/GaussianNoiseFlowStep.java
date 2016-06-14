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

public class GaussianNoiseFlowStep extends FlowStep {

    public GaussianNoiseFlowStep(Step step, EventBus eventBus) {
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

                double p = 0.1;
                double sigma = 10;

                int height = input.getHeight();
                int width = input.getWidth();

                ImageBuilder builder = new ImageBuilder(width, height);
                for (int yCounter = 0; yCounter < height; yCounter++) {
                    for (int xCounter = 0; xCounter < width; xCounter++) {

                        if (Math.random() < p) {
                            double u1 = Math.random();
                            double u2 = Math.random();
                            int v = (int) (sigma * Math.sqrt(-2. * Math.log(u1)) * Math.cos(2. * Math.PI * u2));

                            Color color = input.getColor(xCounter, yCounter);

                            Color newColor = new Color(color.getRed() + v, color.getGreen() + v, color.getBlue() + v);
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