package pl.edu.uj.kimage.configuration;

public enum PropertyName {
    NUMBER_OF_THREADS("threads");

    private String key;

    PropertyName(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}