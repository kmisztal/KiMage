package pl.edu.uj.kimage.eventbus;

public interface MessageTranslator {
    String serialize(Object object);

    <T> T deserialize(Class<T> objectClass, String message);
}
