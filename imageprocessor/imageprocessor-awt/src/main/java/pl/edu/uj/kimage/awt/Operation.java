package pl.edu.uj.kimage.awt;

import pl.edu.uj.kimage.plugin.model.Image;

public interface Operation<T> {
    Image process(Image image, T... params);
}
