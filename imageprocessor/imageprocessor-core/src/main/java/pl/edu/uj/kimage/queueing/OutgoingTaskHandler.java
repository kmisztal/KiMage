package pl.edu.uj.kimage.queueing;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.uj.kimage.api.Result;
import pl.edu.uj.kimage.eventbus.JsonMessageTranslator;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public final class OutgoingTaskHandler implements Runnable {
    private static Vertx vertx;
    private Queue<CalculationResult> taskQueue = new LinkedBlockingQueue<>();
    private JsonMessageTranslator messageTranslator = new JsonMessageTranslator();
    private volatile boolean running = true;
    private static final Logger logger = LogManager.getRootLogger();

    public OutgoingTaskHandler() {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                vertx = res.result();
            } else {
                logger.debug("Unable to connect to cluster");
            }
        });
    }

    @Override
    public void run() {
        while (running) {
            if (vertx != null) {
                CalculationResult calculationResult = taskQueue.poll();
                EventBus eventBus = vertx.eventBus();
                Object result = calculationResult.getResult();
                String className = calculationResult.getResultClass().getName();
                Result resultJson = new Result(className, messageTranslator.serialize(result).getBytes());
                logger.debug("Publishing result for task " + calculationResult.getTaskId());
                eventBus.publish(calculationResult.getTaskId(), messageTranslator.serialize(resultJson));
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
