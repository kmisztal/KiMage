package pl.edu.uj.kimage.queueing;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import pl.edu.uj.kimage.api.ImageDataBuilder;
import pl.edu.uj.kimage.api.Task;
import pl.edu.uj.kimage.eventbus.JsonMessageTranslator;
import pl.edu.uj.kimage.eventbus.MessageTranslator;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

public class IncomingTaskHandlerTest {

    private IncomingTaskHandler incomingTaskHandler;
    private MessageTranslator messageTranslator = new JsonMessageTranslator();

    @Before
    public void setUp() throws Exception {
        incomingTaskHandler = new IncomingTaskHandler();
        while (incomingTaskHandler.isNotReady()) {
            Thread.sleep(100);
        }
        Thread.sleep(2000);
    }

    @Test(timeout = 25000)
    public void shouldAddReceivedTaskToQueue() throws Exception {
        //Given
        CompletableFuture<String> future = new CompletableFuture<>();
        String taskId = "taskId";
        String task = messageTranslator.serialize(new Task(taskId, new ImageDataBuilder(10, 10).build(), Collections.emptyList()));
        //When
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            Vertx vertx = res.result();
            EventBus eventBus = vertx.eventBus();
            eventBus.send(IncomingTaskHandler.QUEUE_TOPIC, task, messageAsyncResult -> {
                future.complete((String) messageAsyncResult.result().body());
                messageAsyncResult.succeeded();
            });

        });


        //Then
        assertThat(future.get()).isEqualTo("GOT IT");
        assertThat(incomingTaskHandler.takeTask().getTaskId()).isEqualTo(taskId);
    }
}