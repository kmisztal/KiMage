package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.plugin.model.Image;

public class ImageCalculated extends StepResultEvent<Image> {

    public ImageCalculated(int stepNumber, Image loadedImage) {
        super(loadedImage);
        setFlowStepNumber(stepNumber);
    }

    public Image getLoadedImage() {
        return getResult();
    }
}
