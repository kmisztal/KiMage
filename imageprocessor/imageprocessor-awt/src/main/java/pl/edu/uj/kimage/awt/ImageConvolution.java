package pl.edu.uj.kimage.awt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.uj.kimage.plugin.model.Image;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;

public class ImageConvolution implements Operation<Kernel> {
    private final static Logger logger = LogManager.getLogger(ImageConvolution.class);

    @Override
    public Image process(Image image, Kernel... params) {
        Kernel kernel = params[0];
        BufferedImage imgIn = ImageConverter.toBufferedImage(image);
        BufferedImage imgOut = new BufferedImage(imgIn.getWidth(), imgIn.getHeight(), imgIn.getType());
        final BufferedImageOp bio = new ConvolveOp(new java.awt.image.Kernel(kernel.getWidth(), kernel.getHeight(), kernel.getKernel()));

        try {
            bio.filter(imgIn, imgOut);
        } catch (java.lang.IllegalArgumentException ex) {
            logger.warn(ex);
        }
        return ImageConverter.toImage(imgOut);
    }
}
