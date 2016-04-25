package pl.edu.uj.kimage.processingFlow;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.edu.uj.kimage.PluginManifestRepository;
import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.api.Task;
import pl.edu.uj.kimage.plugin.ImageResultEvent;
import pl.edu.uj.kimage.plugin.PluginManifest;
import pl.edu.uj.kimage.plugin.model.Color;
import pl.edu.uj.kimage.plugin.model.Image;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageProcessingFlowTest {

    private static final int FIRST_EVENT = 0;
    private static final int ONE_PIXEL = 1;
    private static final String PLUGIN_NAME = "pluginName";
    private static final PluginManifest PLUGIN_MANIFEST = new PluginManifest(PLUGIN_NAME, TestFlowStep.class, new
            TestFlowStepFactory());
    private static final int INITIAL_STEP_NUMBER = 0;
    private TestEventBus eventBus;
    private PluginManifestRepository manifestRepository;
    private Image image;

    @Before
    public void setUp() throws Exception {
        eventBus = new TestEventBus();
        manifestRepository = new PluginManifestRepository();
        manifestRepository.save(PLUGIN_MANIFEST);
        image = new Image(ONE_PIXEL, ONE_PIXEL, new Color[]{Color.BLACK});
    }

    @Test
    public void shouldStartProcessingFlow() {
        //Given
        ImageProcessingFlowFactory flowFactory = new ImageProcessingFlowFactory();
        Step step = new Step(INITIAL_STEP_NUMBER, PLUGIN_NAME, Arrays.asList(new StepDependency(INITIAL_STEP_NUMBER, Image.class)));
        Task task = new Task("".getBytes(), Arrays.asList(step));
        ImageProcessingFlow imageProcessingFlow = flowFactory.create(manifestRepository, eventBus, task);
        //When
        imageProcessingFlow.start(image);
        //Then
        assertThat((ImageResultEvent) eventBus.getPublishedEvents().get(FIRST_EVENT)).is(new Condition<>(e -> e.getStepNumber()
                == INITIAL_STEP_NUMBER && e.getLoadedImage() == image, "Is message loaded"));
    }

    //TODO add test for processing finish
    @Test
    @Ignore
    public void shouldFinishProcessing() throws InterruptedException {
        //Given
        ImageProcessingFlowFactory flowFactory = new ImageProcessingFlowFactory();
        Step step = new Step(INITIAL_STEP_NUMBER, PLUGIN_NAME, Arrays.asList(new StepDependency(INITIAL_STEP_NUMBER, Image.class)));
        Task task = new Task("".getBytes(), Arrays.asList(step));
        ImageProcessingFlow imageProcessingFlow = flowFactory.create(manifestRepository, eventBus, task);
        //When
        imageProcessingFlow.start(image);
        //Then
        assertThat((TestEvent) eventBus.getPublishedEvents().get(1)).is(new Condition<>(e -> e.getStepNumber() == 1 && e.getResult() == TestFlowStep.RESULT, "Step id correct and has expected result"));
    }
}