package pl.edu.uj.kimage;

import pl.edu.uj.kimage.queueing.IncomingTaskHandler;
import pl.edu.uj.kimage.queueing.OutgoingTaskHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor(); //todo retrieve from properties
    public static void main(String[] args){
        //todo make it more production ready, now it's only for testing
        IncomingTaskHandler incomingTaskHandler = new IncomingTaskHandler();
        OutgoingTaskHandler outgoingTaskHandler = new OutgoingTaskHandler();
        executorService.submit(outgoingTaskHandler);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                outgoingTaskHandler.stop();
            }
        });

    }
}
