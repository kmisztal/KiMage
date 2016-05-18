package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

public final class ImageData implements Serializable {
    private final int width;
    private final int height;
    private final byte[] data;

    public ImageData(@JsonProperty("width") int width, @JsonProperty("height") int height, @JsonProperty("data") byte[] data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageData imageData = (ImageData) o;

        if (width != imageData.width) return false;
        if (height != imageData.height) return false;
        return Arrays.equals(data, imageData.data);

    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
