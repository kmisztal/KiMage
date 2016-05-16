package pl.edu.uj.kimage;

import pl.edu.uj.kimage.queueing.IncomingTaskHandler;
import pl.edu.uj.kimage.queueing.OutgoingTaskHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor(); //todo retrieve from properties
    public static void main(String[] args){
        //todo make it more production ready, now it's only for testing
        //todo load plugins into repo
        ImageProcessor imageProcessor = new ImageProcessor(new IncomingTaskHandler(), new OutgoingTaskHandler(), new PluginManifestRepository());
        imageProcessor.start();
    }
}
