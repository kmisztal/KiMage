package pl.edu.uj.kimage.plugin;

import pl.edu.uj.kimage.plugin.model.Image;

public class ImageLoaded extends StepResultEvent {
    private final Image loadedImage;

    public ImageLoaded(Image loadedImage) {
        this.loadedImage = loadedImage;
    }

    public Image getLoadedImage() {
        return loadedImage;
    }
}
