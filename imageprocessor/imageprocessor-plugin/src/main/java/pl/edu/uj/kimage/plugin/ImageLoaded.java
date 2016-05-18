package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.plugin.model.Image;

public class ImageLoaded extends StepResultEvent<Image> {

    public ImageLoaded(int stepNumber, Image loadedImage) {
        super(loadedImage);
        setFlowStepNumber(stepNumber);
    }

    public Image getLoadedImage() {
        return getResult();
    }
}
