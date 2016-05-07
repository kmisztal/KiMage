package pl.edu.uj.kimage.queueing;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import pl.edu.uj.kimage.api.Result;
import pl.edu.uj.kimage.eventbus.JsonMessageTranslator;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class OutgoingTaskHandler implements Runnable {
    private static Vertx vertx;
    private Queue<CalculationResult> taskQueue = new LinkedBlockingQueue<>();
    private JsonMessageTranslator messageTranslator = new JsonMessageTranslator();
    private volatile boolean running = true;

    public OutgoingTaskHandler() {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                vertx = res.result();
            } else {
                System.out.println("Unable to connect to cluster");
            }
        });
    }

    @Override
    public void run() {
        while (running) {
            if (vertx != null) {
                CalculationResult poll = taskQueue.poll();
                EventBus eventBus = vertx.eventBus();
                Object result = poll.getResult();
                String className = poll.getResultClass().getName();
                Result resultJson = new Result(className, new byte[0]); //todo add result serialization
                eventBus.publish(poll.getTaskId(), messageTranslator.serialize(result));
            } else try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void addFinishedTask(CalculationResult result) {
        taskQueue.add(result);
    }

    public void stop() {
        running = false;
    }
}
