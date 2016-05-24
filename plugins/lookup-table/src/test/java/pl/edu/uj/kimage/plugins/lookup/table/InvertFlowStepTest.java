package pl.edu.uj.kimage.plugins.lookup.table;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.eventbus.EventBus;
import pl.edu.uj.kimage.plugin.ImageCalculated;
import pl.edu.uj.kimage.plugin.StepResultEvent;
import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.Image;
import pl.edu.uj.kimage.plugin.model.ImageBuilder;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class InvertFlowStepTest {
    private static final int ONE_PIXEL = 1;
    private static final int STEP_NUMBER = 1;
    private static final int Y_COORDINATE = 0;
    private static final int X_COORDINATE = 0;
    private EventBus eventBus;
    private Image whiteImage;
    private Image blackImage;

    @Before
    public void setUpd() {
        blackImage = new ImageBuilder(ONE_PIXEL, ONE_PIXEL).withColor(Color.BLACK, X_COORDINATE, Y_COORDINATE).build();
        whiteImage = new ImageBuilder(ONE_PIXEL, ONE_PIXEL).withColor(Color.WHITE, X_COORDINATE, Y_COORDINATE).build();
        eventBus = mock(EventBus.class);
    }

    @Test
    public void publishInvertedImage() {
        // given
        ArrayList<StepDependency> dependencies = new ArrayList<>();
        StepDependency stepDependency = new StepDependency(STEP_NUMBER, ImageCalculated.class);
        dependencies.add(stepDependency);
        InvertFlowStep invertFlowStep = new InvertFlowStep(new Step(STEP_NUMBER, "InvertPlugin", dependencies),
                eventBus);
        ImageCalculated input = new ImageCalculated(STEP_NUMBER, blackImage);

        // when
        invertFlowStep.processRelatedEvent(input);

        // then
        StepResultEvent expectedResult = new ImageCalculated(1, whiteImage);
        Mockito.verify(eventBus).publish(expectedResult);
    }
}