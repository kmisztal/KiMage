package pl.edu.uj.kimage.queueing;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import pl.edu.uj.kimage.api.Task;
import pl.edu.uj.kimage.eventbus.JsonMessageTranslator;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class IncomingTaskHandler {
    public static final String QUEUE_TOPIC = "imageprocessor-cluster-task-queue";
    private Queue<Task> taskQueue = new LinkedBlockingQueue<>();
    private JsonMessageTranslator messageTranslator = new JsonMessageTranslator();
    private volatile Vertx vertx;

    public IncomingTaskHandler() {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                vertx = res.result();
                EventBus eventBus = vertx.eventBus();
                eventBus.consumer(QUEUE_TOPIC).handler(objectMessage -> {
                    Task task = messageTranslator.deserialize(Task.class, (String) objectMessage.body());
                    System.out.println("Got task " + task.getTaskId());
                    taskQueue.add(task);
                    objectMessage.reply("GOT IT");
                });

            } else {
                System.out.println("Unable to connect to the cluster");
            }
        });
    }

    public Task takeTask() {
        return taskQueue.poll();
    }

    public boolean isNotReady() {
        return vertx == null;
    }
}
