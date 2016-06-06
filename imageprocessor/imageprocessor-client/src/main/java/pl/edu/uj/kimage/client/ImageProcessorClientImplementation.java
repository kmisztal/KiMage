package pl.edu.uj.kimage.client;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.uj.kimage.api.*;
import pl.edu.uj.kimage.eventbus.JsonMessageTranslator;
import pl.edu.uj.kimage.eventbus.MessageTranslator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImageProcessorClientImplementation implements ImageProcessorClient {
    public static final String IMAGE_PROCESSOR_PLUGIN_INFO_TOPIC = "imageprocessor-plugin-info";
    private static Vertx vertx;
    private final String topicName;
    private final MessageTranslator messageTranslator;
    private Map<String, PluginInfo> pluginInfos;
    private ImageProcessorInfo firstImageProcessorInfo;
    private static final Logger logger = LogManager.getRootLogger();

    public ImageProcessorClientImplementation(String topicName) {
        this.topicName = topicName;
        this.messageTranslator = new JsonMessageTranslator();
        pluginInfos = new HashMap<>();
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                vertx = res.result();
                vertx.eventBus().consumer(IMAGE_PROCESSOR_PLUGIN_INFO_TOPIC).handler(objectMessage -> {
                    String response = (String) objectMessage.body();
                    ImageProcessorInfo imageProcessorInfo = messageTranslator.deserialize(ImageProcessorInfo.class, response);
                    if (firstImageProcessorInfo == null) {
                        firstImageProcessorInfo = imageProcessorInfo;
                    } else if (firstImageProcessorInfo != imageProcessorInfo) {
                        logger.error("Inconsistent imageprocessors");
                    } else {
                        pluginInfos = imageProcessorInfo.getAvailablePlugins().stream().collect(Collectors.toMap(PluginInfo::getName, Function.identity()));
                    }
                });
            } else {
                logger.error("Unable to connect to cluster");
            }
        });
    }

    @Override
    public Set<String> getListOfPluginName() {
        return pluginInfos.keySet();
    }

    @Override
    public Set<String> getPluginRequiredTypes(String pluginName) {
        return pluginInfos.get(pluginName).getInputClassNames();
    }

    @Override
    public Set<String> getPluginProducedTypes(String pluginName) {
        return pluginInfos.get(pluginName).getOutputClassNames();
    }

    @Override
    public void publishTask(Task task, Consumer<ProcessingResultEvent> callback) {
        while (vertx == null) {
            //todo add queueing so that it doesn't block current thread
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
               logger.catching(e);
                e.printStackTrace();
            }
        }

        MessageConsumer<Object> objectMessageConsumer = vertx.eventBus().consumer(task.getTaskId());
        objectMessageConsumer.handler(e -> {
            ProcessingResultEvent processingResultEvent = prepareProcessingResult(e);
            callback.accept(processingResultEvent);
            objectMessageConsumer.unregister();
        });

        String serializedTask = messageTranslator.serialize(task);
        vertx.eventBus().send(topicName, serializedTask, messageAsyncResult -> {
            if (messageAsyncResult.succeeded()) {
                Message<Object> result = messageAsyncResult.result();
                logger.debug(result.body());
            }
        });
    }

    private ProcessingResultEvent prepareProcessingResult(Message<Object> e) {
        String response = (String) e.body();
        Result result = messageTranslator.deserialize(Result.class, response);
        try {
            Class<?> aClass = Class.forName(result.getClassName());
            messageTranslator.deserialize(aClass, String.valueOf(result.getData()));
        } catch (ClassNotFoundException e1) {
            logger.catching(e1);
            e1.printStackTrace();
        }
        return null;
    }

}
