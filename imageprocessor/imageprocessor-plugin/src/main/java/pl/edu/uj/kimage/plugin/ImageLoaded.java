package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.plugin.model.Image;

public class ImageLoaded extends StepResultEvent<Image> {

    public ImageLoaded(int stepId, Image loadedImage) {
        super(loadedImage);
        setFlowStepId(stepId);
    }

    public Image getLoadedImage() {
        return getResult();
    }
}
