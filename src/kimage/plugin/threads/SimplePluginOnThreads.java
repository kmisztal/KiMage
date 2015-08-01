package kimage.plugin.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kimage.image.Image;
import kimage.image.ImageForThreads;
import kimage.plugin.Plugin;
import kimage.plugins.color.Grayscale;

/**
 *
 * @author Krzysztof
 */
public class SimplePluginOnThreads extends Plugin {

    private final Plugin plugin;

    public SimplePluginOnThreads(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void process(Image imgIn, Image imgOut) {
        plugin.setAttributes(getAttributes());
        
        final int width = imgIn.getWidth();
        final int height = imgIn.getHeight();
        final int outputPixels[] = new int[width * height];
        final int offset = plugin.getBoundaryForThreads();
//~ Begin
        int n = Runtime.getRuntime().availableProcessors();

        
        try {
            ExecutorService pool = Executors.newCachedThreadPool();
            for (int i = 0; i < n; i++) {
                int fromY = i * height / n;
                int toY = (i + 1) * height / n;
                boolean last = i == n - 1;
                pool.submit(() -> {
//~ End                          

                    plugin.process(new ImageForThreads(imgIn, fromY, toY, offset, last),
                                   new ImageForThreads(imgOut, fromY, toY, offset, last));
//~BEgin       
                });
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException ex) {
        }
//~End        
        /**
         * Write the output pixels to the image pixels
         */
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                imgOut.setRGB(x, y, outputPixels[x + y * width]);
//            }
//        }
    }

//    @Override
//    public String getInfo() {
//        return plugin.getInfo() + "(on threads)"; //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public String getName() {
        return plugin.getName() + "(on threads)"; //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
