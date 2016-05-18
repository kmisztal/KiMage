package pl.edu.uj.kimage.plugins.lookup.table;

import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.FlowStep;
import pl.edu.uj.kimage.plugin.ImageLoaded;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.Image;

import java.util.List;

public class InvertFlowStep extends FlowStep {

    public InvertFlowStep(Step step, EventBus eventBus) {
        super(step, eventBus);
    }

    @Override
    public void processRelatedEvent(StepResultEvent event) {
        List<StepDependency> dependencies = getStep().getDependencies();
        StepDependency stepDependency = dependencies.get(0);
        String eventTypeName = stepDependency.getEventTypeName();

        try {
            Class<?> clazz = Class.forName(eventTypeName);
            if (clazz.isInstance(ImageLoaded.class)) {


                ImageLoaded imageLoaded = (ImageLoaded) event;
                Image input = imageLoaded.getLoadedImage();

                int height = input.getHeight();
                int width = input.getWidth();

                // TODO transform logic from legacy package
//                final BufferedImageOp bio = new LookupOp(new ShortLookupTable(0, lut), null);
//                bio.filter(input.getBufferedImage(), imgOut.getBufferedImage());
                for (int yCounter = 0; yCounter < height; yCounter++) {
                    for (int xCounter = 0; xCounter < width; xCounter++) {
                        Color color = input.getColor(xCounter, yCounter);
                        Color invertedColor = Color.WHITE.minus(color.getRed(), color.getGreen(), color.getBlue(), color
                                .getAlpha());
                        // TODO waiting for implementation #41
                    }
                }

                StepResultEvent result = new ImageLoaded(null);
                publish(result);
            }
        } catch (ClassNotFoundException e) {
            // TODO log
        }
    }
}