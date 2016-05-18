package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    private final String className;
    private final byte[] data;

    public Result(@JsonProperty("data") String className, @JsonProperty("className") byte[] data) {
        this.className = className;
        this.data = data;
    }

    public String getClassName() {
        return className;
    }

    public byte[] getData() {
        return data;
    }
}
