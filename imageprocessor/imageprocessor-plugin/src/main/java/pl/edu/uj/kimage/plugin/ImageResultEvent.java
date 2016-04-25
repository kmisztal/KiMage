package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.plugin.model.Image;

public class ImageResultEvent extends StepResultEvent {
    private final Image loadedImage;

    public ImageResultEvent(Image loadedImage) {
        this.loadedImage = loadedImage;
    }

    public Image getLoadedImage() {
        return loadedImage;
    }
}
