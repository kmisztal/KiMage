package kimage.image;

/**
 * @author Krzysztof
 */
public enum ImageType {
    BINARY(BinaryImage.class);
    private final Class class_;

    private ImageType(Class cl) {
        class_ = cl;
    }

    public Class getClassType() {
        return class_;
    }

}
