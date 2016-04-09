package pl.edu.uj.kimage.processingFlow;

import org.junit.Test;
import pl.edu.uj.kimage.Image;
import pl.edu.uj.kimage.eventbus.*;

public class ImageProcessingFlowTest {

    public static final TestEventBus EVENT_BUS = new TestEventBus();

    @Test
    public void shouldProcessImage()
    {
        //Given
        FlowStepRepository flowStepRepository = new FlowStepRepository();
        TestStep flowStep = new TestStep(0, EVENT_BUS);
        flowStepRepository.saveFlowStep(flowStep);
        ImageProcessingFlow flow = new ImageProcessingFlow(flowStepRepository);
        //When
        flow.start(new Image());
        //Then
        //TODO this is not finished
    }
}