package pl.edu.uj.kimage.eventbus;

import com.fasterxml.jackson.databind.SerializationFeature;
import io.vertx.core.json.Json;

public class MessageTranslator {

    static{
        Json.prettyMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }

    public String serialize(Object object){
        return Json.encodePrettily(object);
    }

    public <T> T deserialize(Class<T> objectClass, String message)
    {
        return Json.decodeValue(message, objectClass);
    }
}
