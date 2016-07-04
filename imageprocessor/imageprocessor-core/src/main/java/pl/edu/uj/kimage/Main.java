package pl.edu.uj.kimage;

import pl.edu.uj.kimage.plugin.Plugin;
import pl.edu.uj.kimage.plugins.lookup.table.invert.InvertPlugin;
import pl.edu.uj.kimage.queueing.IncomingTaskHandler;
import pl.edu.uj.kimage.queueing.OutgoingTaskHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor(); //todo retrieve from properties
    public static void main(String[] args){
        //todo make it more production ready, now it's only for testing
        PluginManifestRepository pluginManifestRepository = retrievePluginManifestRepository();
        ImageProcessor imageProcessor = new ImageProcessor(new IncomingTaskHandler(), new OutgoingTaskHandler(), pluginManifestRepository);
        imageProcessor.start();
    }

    private static PluginManifestRepository retrievePluginManifestRepository() {
        //TODO temporary implementation - should be loaded by reflection
        PluginManifestRepository repository = new PluginManifestRepository();
        Plugin invert = new InvertPlugin();
        repository.save(invert.discover());
        return repository;
    }
}
