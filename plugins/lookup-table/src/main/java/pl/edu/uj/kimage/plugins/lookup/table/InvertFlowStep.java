package pl.edu.uj.kimage.plugins.lookup.table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class InvertFlowStep extends FlowStep {

    public static final int ALPHA_VALUE = 0;
    private static final Logger logger = LogManager.getRootLogger();

    public InvertFlowStep(Step step, EventBus eventBus) {
        super(step, eventBus);
    }

    @Override
    public void processRelatedEvent(StepResultEvent event) {
        List<StepDependency> dependencies = getStep().getDependencies();
        StepDependency stepDependency = dependencies.get(0);
        String eventTypeName = stepDependency.getEventTypeName();

        if (eventTypeName.equals(ImageCalculated.class.getCanonicalName())) {
            ImageCalculated imageCalculated = (ImageCalculated) event;
            Image input = imageCalculated.getLoadedImage();

            int height = input.getHeight();
            int width = input.getWidth();

            ImageBuilder builder = new ImageBuilder(width, height);
            for (int yCounter = 0; yCounter < height; yCounter++) {
                for (int xCounter = 0; xCounter < width; xCounter++) {
                    Color color = input.getColor(xCounter, yCounter);
                    Color invertedColor = Color.WHITE.minus(color.getRed(), color.getGreen(), color.getBlue(), ALPHA_VALUE);
                    builder.withColor(invertedColor, xCounter, yCounter);
                }
            }

            Image invertedImage = builder.build();
            StepResultEvent result = new ImageCalculated(event.getStepNumber(), invertedImage);
            publish(result);
        }
    }
}