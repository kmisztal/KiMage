package pl.edu.uj.kimage.eventbus;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public final class JsonMessageTranslator implements MessageTranslator {

    private final ObjectMapper objectMapper;

    public JsonMessageTranslator() {
        this.objectMapper = new ObjectMapper();
        configureMapper(objectMapper);
    }

    private void configureMapper(ObjectMapper objectMapper) {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Override
    public String serialize(Object object) {
        String valueAsString = null;
        try {
            valueAsString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return valueAsString;
    }

    @Override
    public <T> T deserialize(Class<T> objectClass, String message) {
        T readValue = null;
        try {
            readValue = objectMapper.readValue(message, objectClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readValue;
    }
}
