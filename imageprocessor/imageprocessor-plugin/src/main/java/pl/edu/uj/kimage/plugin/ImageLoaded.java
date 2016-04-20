package pl.edu.uj.kimage.plugin;

public class ImageLoaded extends StepResultEvent {
    private final Image loadedImage;

    public ImageLoaded(int stepId, Image loadedImage) {

        this.loadedImage = loadedImage;
        setFlowStepId(stepId);
    }

    public Image getLoadedImage() {
        return loadedImage;
    }
}
