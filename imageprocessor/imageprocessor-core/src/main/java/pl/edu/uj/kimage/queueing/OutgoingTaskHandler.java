package pl.edu.uj.kimage.queueing;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.uj.kimage.api.Result;
import pl.edu.uj.kimage.eventbus.JsonMessageTranslator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class OutgoingTaskHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(OutgoingTaskHandler.class);
    private static Vertx vertx;
    private BlockingQueue<CalculationResult> taskQueue = new LinkedBlockingQueue<>();
    private JsonMessageTranslator messageTranslator = new JsonMessageTranslator();
    private volatile boolean running = true;

    public OutgoingTaskHandler() {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                vertx = res.result();
            } else {
                logger.error("Unable to connect to cluster");
            }
        });
    }

    @Override
    public void run() {
        while (running) {
            if (vertx != null) {
                CalculationResult calculationResult = takeTask();
                EventBus eventBus = vertx.eventBus();
                Object result = calculationResult.getResult();
                String className = calculationResult.getResultClass().getName();
                Result resultJson = new Result(className, messageTranslator.serialize(result).getBytes());
                logger.debug("Publishing result for task " + calculationResult.getTaskId());
                eventBus.publish(calculationResult.getTaskId(), messageTranslator.serialize(resultJson));
            } else try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.catching(e);
            }

        }
    }

    private CalculationResult takeTask() {
        CalculationResult calculationResult = null;
        try {
            calculationResult = taskQueue.take();
        } catch (InterruptedException e) {
            logger.catching(e);
        }
        return calculationResult;
    }


    public void addFinishedTask(CalculationResult result) {
        taskQueue.add(result);
    }

    public void stop() {
        running = false;
    }
}
