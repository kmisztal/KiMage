package pl.edu.uj.kimage.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcessingResultEvent {
    private final String resultType;
    private final byte[] data;

    public ProcessingResultEvent(@JsonProperty("resultType") String resultType, @JsonProperty("data") byte[] data) {
        this.resultType = resultType;
        this.data = data;
    }

    public String getResultType() {
        return resultType;
    }

    public byte[] getData() {
        return data;
    }
}
