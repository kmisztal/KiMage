package pl.edu.uj.kimage.processingFlow;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.edu.uj.kimage.Image;
import pl.edu.uj.kimage.PluginManifestRepository;
import pl.edu.uj.kimage.api.Step;
import pl.edu.uj.kimage.api.StepDependency;
import pl.edu.uj.kimage.api.Task;
import pl.edu.uj.kimage.plugin.PluginManifest;
import pl.edu.uj.kimage.plugin.StepResultEvent;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageProcessingFlowTest {

    public static final String PLUGIN_NAME = "pluginName";
    public static final PluginManifest PLUGIN_MANIFEST = new PluginManifest(PLUGIN_NAME, TestFlowStep.class, new TestFlowStepFactory());
    public TestEventBus eventBus;
    private PluginManifestRepository manifestRepository;

    @Before
    public void setUp() throws Exception {
        eventBus = new TestEventBus();
        manifestRepository = new PluginManifestRepository();
        manifestRepository.save(PLUGIN_MANIFEST);
    }

    @Test
    public void shouldStartProcessingFlow() {
        //Given
        ImageProcessingFlowFactory flowFactory = new ImageProcessingFlowFactory();
        Step step = new Step(0, PLUGIN_NAME, Arrays.asList(new StepDependency(0, Image.class)));
        Task task = new Task("".getBytes(), Arrays.asList(step));
        ImageProcessingFlow imageProcessingFlow = flowFactory.create(manifestRepository, eventBus, task);
        Image image = new Image();
        //When
        imageProcessingFlow.start(image);
        //Then
        assertThat(eventBus.getPublishedEvents()).startsWith(new StepResultEvent<>(0, image));
    }

    //TODO add test for processing finish

    @Test
    @Ignore
    public void shoudlFinishProcessing() {
        //Given
        ImageProcessingFlowFactory flowFactory = new ImageProcessingFlowFactory();
        Step step = new Step(0, PLUGIN_NAME, Arrays.asList(new StepDependency(0, Image.class)));
        Task task = new Task("".getBytes(), Arrays.asList(step));
        ImageProcessingFlow imageProcessingFlow = flowFactory.create(manifestRepository, eventBus, task);
        Image image = new Image();
        //When
        imageProcessingFlow.start(image);
        //Then
        assertThat(eventBus.getPublishedEvents()).endsWith(new StepResultEvent<>(1, TestFlowStep.TEST_RESULT));
    }
}