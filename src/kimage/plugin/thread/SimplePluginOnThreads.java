package kimage.plugin.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kimage.image.Image;
import kimage.image.ImageForThreads;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public class SimplePluginOnThreads extends Plugin {

    private final Plugin plugin;

    public SimplePluginOnThreads(ConcurrencyReady plugin) {
        this.plugin = (Plugin) plugin;
    }

    @Override
    public void process(Image imgIn, Image imgOut) {
        plugin.setAttributes(getAttributes());
        
        final int width = imgIn.getWidth();
        final int height = imgIn.getHeight();
        
        final int offset = plugin.getBoundaryForThreads();
        int n = Runtime.getRuntime().availableProcessors();
        
        try {
            ExecutorService pool = Executors.newCachedThreadPool();
            for (int i = 0; i < n; i++) {
                int fromY = i * height / n;
                int toY = (i + 1) * height / n;
                boolean last = i == n - 1;
                pool.submit(() -> {
                    plugin.process(new ImageForThreads(imgIn, fromY, toY, offset, last),
                                   new ImageForThreads(imgOut, fromY, toY, offset, last));      
                });
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public String getName() {
        return plugin.getName() + "(on threads)"; 
    }

}
