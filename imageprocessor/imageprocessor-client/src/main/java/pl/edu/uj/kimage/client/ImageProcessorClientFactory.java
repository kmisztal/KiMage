package pl.edu.uj.kimage.client;

public class ImageProcessorClientFactory {

    public static final String IMAGEPROCESSOR_TASK_TOPIC = "imageprocessor-cluster-task-queue";

    public ImageProcessorClient create(){
        return new ImageProcessorClientImplementation(IMAGEPROCESSOR_TASK_TOPIC);
    }
}
