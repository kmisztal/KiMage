package kimage.plugins.color;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.plugin.thread.ConcurrencyReady;

public class SimpleInvert extends Plugin implements ConcurrencyReady {

    @Override
    public void process(Image imgIn, Image imgOut) {
        final int height = imgIn.getHeight();
        final int widht = imgIn.getWidth();

        for (int x = 0; x < widht; x++) {
            for (int y = 0; y < height; y++) {
                final int red = imgIn.getRed(x, y);
                final int green = imgIn.getGreen(x, y);
                final int blue = imgIn.getBlue(x, y);

                imgOut.setRGB(x, y, 255 - red, 255 - green, 255 - blue);
            }
        }
    }

    @Override
    public int getBoundaryForThreads() {
        return 0;
    }
}
